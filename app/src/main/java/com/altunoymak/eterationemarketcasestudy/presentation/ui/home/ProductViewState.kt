package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem

data class ProductViewState(
    val isLoading: Boolean? = null,
    val products: List<ProductResponseItem>? = null,
    val errorMessage: String? = null
)