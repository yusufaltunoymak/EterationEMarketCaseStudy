package com.altunoymak.eterationemarketcasestudy.domain.repository

import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {
    suspend fun addFavoriteProduct(product: FavoriteProduct)
    suspend fun removeFavoriteProduct(productId: String)
    suspend fun getFavoriteProduct(productId: String): FavoriteProduct?
    suspend fun getFavoriteProducts(): Flow<List<FavoriteProduct>>
}