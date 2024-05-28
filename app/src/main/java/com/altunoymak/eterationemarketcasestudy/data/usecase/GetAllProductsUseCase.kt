package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.repository.ProductRepositoryImpl
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(private val productRepositoryImpl: ProductRepositoryImpl) {
    suspend operator fun invoke() = productRepositoryImpl.getAllProducts()
}