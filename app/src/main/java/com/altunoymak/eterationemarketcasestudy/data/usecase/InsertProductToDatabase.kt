package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.ProductDatabaseRepositoryImpl
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertProductToDatabase @Inject constructor(private val productDatabaseRepositoryImpl: ProductDatabaseRepositoryImpl) {
    suspend operator fun invoke(product : Product) : Flow<Response<Boolean>> = productDatabaseRepositoryImpl.insertProduct(product = product)
}