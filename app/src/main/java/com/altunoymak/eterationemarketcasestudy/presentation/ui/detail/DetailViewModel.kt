package com.altunoymak.eterationemarketcasestudy.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.ResponseStatus
import com.altunoymak.eterationemarketcasestudy.data.usecase.AddFavoriteProductUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetFavoriteProductUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.InsertProductToDatabase
import com.altunoymak.eterationemarketcasestudy.data.usecase.RemoveFavoriteProductUseCase
import com.altunoymak.eterationemarketcasestudy.presentation.ui.cart.ProductDatabaseViewState
import com.altunoymak.eterationemarketcasestudy.util.mapToFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val insertProductToDatabase: InsertProductToDatabase,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val getFavoriteProductUseCase: GetFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow(ProductDatabaseViewState())
    val viewState = _viewState.asStateFlow()
    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isFavorited
    fun insertToDatabase(product: Product) = viewModelScope.launch {
        insertProductToDatabase(product).collect {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            isInsertDatabase = true
                        )
                    }
                }

                ResponseStatus.ERROR -> {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage,
                            isInsertDatabase = false
                        )
                    }
                }

                else -> {
                    _viewState.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = null,
                            isInsertDatabase = false
                        )
                    }
                }
            }
        }
    }


    fun addProductToFavorites(product: Product) = viewModelScope.launch {
        val favoriteProduct = mapToFavorite(product,true)
        addFavoriteProductUseCase(favoriteProduct)
        _isFavorited.value = true
    }
    fun removeFavoriteProduct(productId: String) = viewModelScope.launch {
        removeFavoriteProductUseCase(productId)
        _isFavorited.value = false
    }

    fun checkIfFavoriteProduct(productId: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val isFavorite = getFavoriteProductUseCase(productId) != null
            result.value = isFavorite
        }
        return result
    }
}