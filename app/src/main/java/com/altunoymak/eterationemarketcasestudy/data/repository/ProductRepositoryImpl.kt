package com.altunoymak.eterationemarketcasestudy.data.repository

import com.altunoymak.eterationemarketcasestudy.data.remote.ProductAPI
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponse
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import com.altunoymak.eterationemarketcasestudy.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val api : ProductAPI) : ProductRepository {
    override suspend fun getAllProducts(): Flow<Response<ProductResponse>> {
        return flow {
            emit(Response.Loading)
            try {
                val response = api.getProducts()
                emit(Response.Success(response))
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading products"))
                return@flow
            }
            catch (e : HttpException) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading products"))
                return@flow
            }
            catch (e : Exception) {
                e.printStackTrace()
                emit(Response.Error(_message = "Error loading products"))
                return@flow
            }
        }
    }
}