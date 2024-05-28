package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.repository.FavoriteProductRepositoryImpl
import javax.inject.Inject

class GetFavoriteProductUseCase @Inject constructor(private val repository: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(productId: String) = repository.getFavoriteProduct(productId)
}