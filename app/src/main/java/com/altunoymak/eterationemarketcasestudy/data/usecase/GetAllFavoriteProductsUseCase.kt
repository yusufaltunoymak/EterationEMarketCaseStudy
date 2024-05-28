package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import com.altunoymak.eterationemarketcasestudy.data.repository.FavoriteProductRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteProductsUseCase @Inject constructor(private val repository: FavoriteProductRepositoryImpl) {
    suspend operator fun invoke(): Flow<List<FavoriteProduct>> {
        return repository.getFavoriteProducts()
    }
}