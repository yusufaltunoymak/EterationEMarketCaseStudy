package com.altunoymak.eterationemarketcasestudy.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetAllOrdersUseCase
import com.altunoymak.eterationemarketcasestudy.presentation.ui.favorite.FavoriteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow(ProfileViewState())
    val viewState = _viewState.asStateFlow()

    init {
        getAllOrders()
    }

    fun getAllOrders() = viewModelScope.launch {
        getAllOrdersUseCase.invoke().collect { orderList ->
            _viewState.value = ProfileViewState(isLoading = false, errorMessage = null, orderList = orderList)
        }
    }
}