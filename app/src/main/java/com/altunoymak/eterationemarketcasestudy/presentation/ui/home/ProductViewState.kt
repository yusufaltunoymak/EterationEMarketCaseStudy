package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem

data class ProductViewState(
    val isLoading: Boolean? = null,
    val products: List<ProductResponseItem> = emptyList(),
    val isInsertDatabase : Boolean? = null,
    val modelList: List<String> = emptyList(),
    val brandList: List<String> = emptyList(),
    val errorMessage: String? = null
)