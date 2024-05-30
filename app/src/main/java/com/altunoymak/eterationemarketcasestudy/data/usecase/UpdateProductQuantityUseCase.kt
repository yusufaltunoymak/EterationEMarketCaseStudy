package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.ProductDatabaseRepositoryImpl
import com.altunoymak.eterationemarketcasestudy.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductQuantity @Inject constructor(
    private val productDatabaseRepository: ProductDatabaseRepositoryImpl
) {
    suspend operator fun invoke(id: Int, quantity: Int): Flow<Response<Boolean>> {
        return productDatabaseRepository.updateProductQuantity(id, quantity)
    }
}
