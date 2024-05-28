package com.altunoymak.eterationemarketcasestudy.data.local

import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import com.altunoymak.eterationemarketcasestudy.domain.repository.ProductDatabaseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDatabaseRepositoryImpl @Inject constructor(private val dao : ProductDao) : ProductDatabaseRepository {
    override fun getAllProducts(): Flow<Response<List<Product>>> = channelFlow {
        try {
            trySend(Response.Loading)
            val productsFlow = dao.getAllProducts()
            productsFlow.collect { products ->
                val distinctProducts = products.distinctBy { it.productId }
                trySend(Response.Success(distinctProducts))
            }
        } catch (e: Exception) {
            trySend(Response.Error(_message = e.message.toString()))
        }
        awaitClose()
    }

    override suspend fun insertProduct(product: Product): Flow<Response<Boolean>> {
        return flow {
            try {
                emit(Response.Loading)
                dao.insertProduct(product = product)
                emit(Response.Success(true))
            }
            catch (e : Exception) {
                emit(Response.Error(_message = e.message.toString()))
            }
        }
    }

    override suspend fun updateProductQuantity(id: Int, quantity: Int): Flow<Response<Boolean>> {
        return flow {
            try {
                emit(Response.Loading)
                dao.updateProductQuantity(id, quantity)
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(_message = e.message.toString()))
            }
        }
    }

    override suspend fun deleteProduct(id: Int): Flow<Response<Boolean>> {
        return flow {
            try {
                emit(Response.Loading)
                dao.deleteProduct(id)
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(_message = e.message.toString()))
            }
        }
    }
}