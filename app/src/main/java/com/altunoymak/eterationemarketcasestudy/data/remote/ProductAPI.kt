package com.altunoymak.eterationemarketcasestudy.data.remote

import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponse
import retrofit2.http.GET

interface ProductAPI {
@GET("products")
suspend fun getProducts() : ProductResponse
}