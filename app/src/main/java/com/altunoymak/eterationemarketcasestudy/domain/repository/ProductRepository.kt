package com.altunoymak.eterationemarketcasestudy.domain.repository

import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponse
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts() : Flow<Response<ProductResponse>>
}