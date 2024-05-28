package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.repository.FavoriteProductRepositoryImpl
import javax.inject.Inject

class RemoveFavoriteProductUseCase @Inject constructor(private val favoriteProductRepositoryImpl: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(productId: String) = favoriteProductRepositoryImpl.removeFavoriteProduct(productId)
}