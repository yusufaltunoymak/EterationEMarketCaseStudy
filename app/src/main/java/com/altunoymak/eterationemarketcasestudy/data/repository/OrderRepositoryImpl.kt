package com.altunoymak.eterationemarketcasestudy.data.repository

import com.altunoymak.eterationemarketcasestudy.data.local.OrderDao
import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import com.altunoymak.eterationemarketcasestudy.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDao: OrderDao) : OrderRepository {
    override fun getAllOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }

    override suspend fun insertOrder(order: Order): Flow<Response<Boolean>> {
        return flow {
            try {
                emit(Response.Loading)
                orderDao.insertOrder(order)
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(_message = e.message.toString()))
            }
        }
    }

    override suspend fun deleteOrder(id: Int) {
        orderDao.deleteOrder(id)
    }

    override suspend fun getOrder(productId: String): Order? {
        return orderDao.getOrder(productId)
    }
}
