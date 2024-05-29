package com.altunoymak.eterationemarketcasestudy.presentation.ui.cart

import com.altunoymak.eterationemarketcasestudy.data.local.model.Product

data class ProductDatabaseViewState(
    val isLoading: Boolean? = null,
    val isInsertDatabase : Boolean = false,
    val productList : List<Product>? = null,
    val isFavorited: Boolean = false,
    val quantity : Int = 0,
    val isCompleteOrder : Boolean = false,
    val errorMessage : String? = null
)
