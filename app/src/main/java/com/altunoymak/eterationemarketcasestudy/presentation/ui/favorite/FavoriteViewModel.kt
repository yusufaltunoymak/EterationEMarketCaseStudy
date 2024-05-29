package com.altunoymak.eterationemarketcasestudy.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetAllFavoriteProductsUseCase
import com.altunoymak.eterationemarketcasestudy.presentation.ui.home.ProductViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val getAllFavoriteProductsUseCase: GetAllFavoriteProductsUseCase) : ViewModel() {
    private var _viewState = MutableStateFlow(FavoriteViewState())
    val viewState = _viewState.asStateFlow()

    init {
        getFavoriteProducts()
    }

    private fun getFavoriteProducts() = viewModelScope.launch {
        getAllFavoriteProductsUseCase.invoke().collect { favoriteProducts ->
            _viewState.value = FavoriteViewState(isLoading = false, errorMessage = null, favList = favoriteProducts)
        }
    }
}