package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.repository.OrderRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(private val repositoryImpl: OrderRepositoryImpl) {
    suspend operator fun invoke() : Flow<List<Order>> {
        return repositoryImpl.getAllOrders()
    }
}