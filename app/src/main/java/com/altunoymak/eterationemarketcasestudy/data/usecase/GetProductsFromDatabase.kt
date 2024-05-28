package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.ProductDatabaseRepositoryImpl
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsFromDatabase @Inject constructor(private val productDatabaseRepositoryImpl: ProductDatabaseRepositoryImpl) {
    operator fun invoke(): Flow<Response<List<Product>>> = productDatabaseRepositoryImpl.getAllProducts()
}