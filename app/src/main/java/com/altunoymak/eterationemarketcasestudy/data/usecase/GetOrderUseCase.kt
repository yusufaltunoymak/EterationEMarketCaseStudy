package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.repository.OrderRepositoryImpl
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(private val orderRepository: OrderRepositoryImpl) {
    suspend operator fun invoke(productId: String): Order? {
        return orderRepository.getOrder(productId)
    }
}