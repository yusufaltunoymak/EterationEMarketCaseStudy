package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem
import com.altunoymak.eterationemarketcasestudy.data.response.ResponseStatus
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class ProductViewModel @Inject constructor(private val getAllProductsUseCase: GetAllProductsUseCase) : ViewModel() {
    private var _viewState = MutableStateFlow(ProductViewState())
    val viewState = _viewState.asStateFlow()
    private var allProducts = listOf<ProductResponseItem>()

    // Current page for pagination
    private var currentPage = 0

    // Number of items per page
    private val itemsPerPage = 4

    fun getItemsPerPage(): Int {
        return itemsPerPage
    }

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
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
        val startIndex = currentPage * itemsPerPage
        val endIndex = min(startIndex + itemsPerPage, allProducts.size)

        if(startIndex < endIndex) {
            val newItems = allProducts.subList(startIndex, endIndex)
            _viewState.update { viewState ->
                viewState.copy(
                    isLoading = false,
                    products = viewState.products?.plus(newItems)
                )
            }
            Log.d("ProductViewModel", "Loaded ${newItems.size} more items")
            currentPage++
        }
        else {
            Log.d("ProductViewModel", "No more items to load")
        }
    }
}