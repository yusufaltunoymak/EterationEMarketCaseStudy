package com.altunoymak.eterationemarketcasestudy.presentation.ui.home

import androidx.lifecycle.LiveData
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem

interface ProductActions {
    fun addProductToFavorites(product: ProductResponseItem)
    fun removeFavoriteProduct(productId: String)
    fun checkIfFavoriteProduct(productId: String): LiveData<Boolean>
    fun addToCart(product: Product)
}