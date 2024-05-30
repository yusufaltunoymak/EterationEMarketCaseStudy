package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.ProductDatabaseRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartItemCountUseCase @Inject constructor(private val productDatabaseRepository: ProductDatabaseRepositoryImpl) {
    operator fun invoke(): Flow<Int> = productDatabaseRepository.getAllProducts().map { it.data?.size ?: 0 }
}