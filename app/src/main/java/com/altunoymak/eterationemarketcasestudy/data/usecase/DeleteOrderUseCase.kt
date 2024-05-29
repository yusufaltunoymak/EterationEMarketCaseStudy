package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.repository.OrderRepositoryImpl
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(private val orderRepository: OrderRepositoryImpl) {
    suspend operator fun invoke(id: Int) {
        orderRepository.deleteOrder(id)
    }
}