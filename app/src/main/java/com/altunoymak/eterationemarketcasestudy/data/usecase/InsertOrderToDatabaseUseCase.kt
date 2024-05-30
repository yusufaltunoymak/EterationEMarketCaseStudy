package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.repository.OrderRepositoryImpl
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertOrderToDatabaseUseCase @Inject constructor(private val orderRepository: OrderRepositoryImpl) {
    suspend operator fun invoke(order: Order) : Flow<Response<Boolean>> {
        return orderRepository.insertOrder(order)
    }
}