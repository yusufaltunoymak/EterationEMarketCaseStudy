package com.altunoymak.eterationemarketcasestudy.data.repository

import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProductDao
import com.altunoymak.eterationemarketcasestudy.domain.repository.FavoriteProductRepository
import javax.inject.Inject


class FavoriteProductRepositoryImpl @Inject constructor(private val dao: FavoriteProductDao) : FavoriteProductRepository {
    override suspend fun addFavoriteProduct(product: FavoriteProduct) = dao.addFavoriteProduct(product)
    override suspend fun removeFavoriteProduct(productId: String) = dao.removeFavoriteProduct(productId)
    override suspend fun getFavoriteProduct(productId: String): FavoriteProduct? {
        return dao.getFavoriteProduct(productId)
    }
}