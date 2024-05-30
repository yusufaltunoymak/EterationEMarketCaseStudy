package com.altunoymak.eterationemarketcasestudy.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.response.ResponseStatus
import com.altunoymak.eterationemarketcasestudy.data.usecase.DeleteProductFromDatabase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetOrderUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.GetProductsFromDatabase
import com.altunoymak.eterationemarketcasestudy.data.usecase.InsertOrderToDatabaseUseCase
import com.altunoymak.eterationemarketcasestudy.data.usecase.UpdateProductQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDatabaseViewModel @Inject constructor(
    private val getAllProductsUseCase: GetProductsFromDatabase,
    private val updateProductQuantityUseCase: UpdateProductQuantity,
    private val deleteProductFromDatabase: DeleteProductFromDatabase,
    private val insertOrderToDatabaseUseCase: InsertOrderToDatabaseUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    ) : ViewModel() {
    private var _viewState = MutableStateFlow(ProductDatabaseViewState())
    val viewState = _viewState.asStateFlow()

    init {
        getProductFromDatabase()
    }

    private fun getProductFromDatabase() {
        viewModelScope.launch {
            getAllProductsUseCase().collectLatest { response ->
                when (response.status) {
                    ResponseStatus.SUCCESS -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = null,
                                productList = response.data,
                                quantity = response.data?.sumOf { it.quantity } ?: 0
                            )
                        }
                    }

                    ResponseStatus.ERROR -> {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = it.errorMessage,
                                productList = emptyList()
                            )
                        }
                    }

                    else -> {
                        _viewState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null,
                                productList = emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateProductQuantity(id: Int, quantity: Int) {
        viewModelScope.launch {
            updateProductQuantityUseCase(id, quantity).collectLatest { response ->
                when (response.status) {
                    ResponseStatus.SUCCESS -> {
                        getProductFromDatabase()
                    }
                    ResponseStatus.ERROR -> {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message
                            )
                        }
                    }
                    else -> {
                        _viewState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                }
            }
        }
    }
    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            deleteProductFromDatabase.invoke(id).collect { response ->
                when (response.status) {
                    ResponseStatus.SUCCESS -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                productList = viewState.productList?.filter { it.id != id },
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    ResponseStatus.LOADING -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                    else -> {
                        _viewState.update { viewState ->
                            viewState.copy(
                                isLoading = false,
                                errorMessage = response.message
                            )
                        }
                    }
                }
            }
        }
    }
    fun insertOrder(order : Order) {
        viewModelScope.launch {
            insertOrderToDatabaseUseCase.invoke(order).collect {response ->
                when(response.status) {
                    ResponseStatus.SUCCESS -> {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                isCompleteOrder = true,
                                errorMessage = null
                            )
                        }
                    }
                    ResponseStatus.ERROR -> {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                isCompleteOrder = false,
                                errorMessage = response.message
                            )
                        }
                    }
                    else -> {
                        _viewState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                }
            }
        }
    }
    fun getOrder(productId: String): LiveData<Order?> {
        return liveData {
            val order = getOrderUseCase(productId)
            emit(order)
        }
    }
}