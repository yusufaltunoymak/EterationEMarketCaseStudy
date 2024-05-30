package com.altunoymak.eterationemarketcasestudy.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.usecase.DeleteOrderUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetAllOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase
) : ViewModel() {
    private var _viewState = MutableStateFlow(ProfileViewState())
    val viewState = _viewState.asStateFlow()

    init {
        getAllOrders()
    }

    private fun getAllOrders() = viewModelScope.launch {
        getAllOrdersUseCase.invoke().collect { orderList ->
            _viewState.value = ProfileViewState(isLoading = false, errorMessage = null, orderList = orderList)
        }
    }
    fun deleteOrder(id:Int) {
        viewModelScope.launch {
            deleteOrderUseCase.invoke(id)
        }
    }
}