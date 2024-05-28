package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import com.altunoymak.eterationemarketcasestudy.data.repository.FavoriteProductRepositoryImpl
import javax.inject.Inject

class AddFavoriteProductUseCase @Inject constructor(private val repository: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(product: FavoriteProduct) = repository.addFavoriteProduct(product)
}