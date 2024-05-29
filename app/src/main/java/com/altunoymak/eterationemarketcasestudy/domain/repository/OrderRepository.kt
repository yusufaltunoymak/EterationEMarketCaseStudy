package com.altunoymak.eterationemarketcasestudy.domain.repository

import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAllOrders(): Flow<List<Order>>
    suspend fun insertOrder(order: Order) : Flow<Response<Boolean>>
    suspend fun deleteOrder(order: Order)
    suspend fun getOrder(productId: String): Order?

}