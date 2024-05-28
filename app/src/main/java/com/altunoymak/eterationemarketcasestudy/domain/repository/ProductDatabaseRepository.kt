package com.altunoymak.eterationemarketcasestudy.domain.repository

import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow

interface ProductDatabaseRepository {
    fun getAllProducts(): Flow<Response<List<Product>>>
    suspend fun insertProduct(product: Product) : Flow<Response<Boolean>>
    suspend fun updateProductQuantity(id: Int, quantity: Int): Flow<Response<Boolean>>
    suspend fun deleteProduct(id: Int): Flow<Response<Boolean>>

}