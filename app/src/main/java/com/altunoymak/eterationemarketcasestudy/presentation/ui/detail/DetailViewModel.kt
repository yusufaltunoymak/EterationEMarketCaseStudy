package com.altunoymak.eterationemarketcasestudy.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.ResponseStatus
import com.altunoymak.eterationemarketcasestudy.data.usecase.InsertProductToDatabase
import com.altunoymak.eterationemarketcasestudy.presentation.ui.cart.ProductDatabaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val insertProductToDatabase: InsertProductToDatabase
) : ViewModel() {
    private var _viewState = MutableStateFlow(ProductDatabaseViewState())
    val viewState = _viewState.asStateFlow()
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
}