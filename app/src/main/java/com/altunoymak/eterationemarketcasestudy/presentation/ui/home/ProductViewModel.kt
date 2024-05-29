package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem
import com.altunoymak.eterationemarketcasestudy.data.response.ResponseStatus
import com.altunoymak.eterationemarketcasestudy.data.usecase.AddFavoriteProductUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetAllProductsUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetCartItemCountUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetFavoriteProductUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.InsertProductToDatabase
import com.altunoymak.eterationemarketcasestudy.data.usecase.RemoveFavoriteProductUseCase
import com.altunoymak.eterationemarketcasestudy.util.productToResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase,
    private val getFavoriteProductUseCase: GetFavoriteProductUseCase,
    private val insertProductToDatabase: InsertProductToDatabase,
    private val getCartItemCountUseCase: GetCartItemCountUseCase
    ) : ViewModel() {
    private var _viewState = MutableStateFlow(ProductViewState())
    val viewState = _viewState.asStateFlow()
    private var allProducts = listOf<ProductResponseItem>()
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _cartItemCount = MutableLiveData<Int>(0)
    val cartItemCount: LiveData<Int> = _cartItemCount

    private var currentPage = 0

    private val itemsPerPage = 4

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun getFilteredProducts(): List<ProductResponseItem> {
        return allProducts.filter { product ->
                product.name?.contains(searchText.value, ignoreCase = true) ?: false
        }
    }

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase.invoke().collect { response ->
                when (response.status) {
                    ResponseStatus.LOADING -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                products = emptyList(),
                                errorMessage = null
                            )
                        }

                    }

                    ResponseStatus.SUCCESS -> {
                        response.data?.let {
                            allProducts = response.data
                            _viewState.update { viewState ->
                                viewState.copy(
                                    isLoading = false,
                                    products = allProducts
                                )
                            }
                            loadMoreItems()
                        }
                    }

                    else -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                errorMessage = response.message
                            )
                        }
                    }

                }
            }
        }
    }
    fun loadMoreItems() {
        viewModelScope.launch {
            _viewState.update {
                it.copy(isLoading = true)
            }
            val startIndex = currentPage * itemsPerPage
            val endIndex = min(startIndex + itemsPerPage, allProducts.size)
            if (startIndex < allProducts.size) {
                val newItems = allProducts.subList(startIndex, endIndex)
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        products = it.products + newItems
                    )
                }
                currentPage++
            } else {
                _viewState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
    fun addProductToFavorites(product: ProductResponseItem) = viewModelScope.launch {
        val favoriteProduct = productToResponseItem(product,true)
        addFavoriteProductUseCase(favoriteProduct)
    }

    fun removeFavoriteProduct(productId: String) = viewModelScope.launch {
        removeFavoriteProductUseCase(productId)
    }

    fun checkIfFavoriteProduct(productId: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val isFavorite = getFavoriteProductUseCase(productId) != null
            result.value = isFavorite
        }
        return result
    }
    fun addToCart(product: Product) {
        viewModelScope.launch {
            insertProductToDatabase(product).collect { response ->
                when(response.status) {
                    ResponseStatus.SUCCESS -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = null,
                                isInsertDatabase = true
                            )
                        }
                        _viewState.update { viewState ->
                            viewState.copy(
                                isInsertDatabase = false
                            )
                        }
                        getCartItemCount()
                    }

                    ResponseStatus.LOADING -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null,
                                isInsertDatabase = false
                            )
                        }
                    }
                    else -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = response.message,
                                isInsertDatabase = false
                            )
                        }
                    }
                }
            }
        }
    }
    fun getCartItemCount() {
        viewModelScope.launch {
            getCartItemCountUseCase().collect { count ->
                _cartItemCount.postValue(count)
            }
        }
    }
}