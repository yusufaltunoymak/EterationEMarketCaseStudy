package com.altunoymak.eterationemarketcasestudy.data.usecase

import com.altunoymak.eterationemarketcasestudy.data.local.ProductDatabaseRepositoryImpl
import javax.inject.Inject

class DeleteProductFromDatabase @Inject constructor(private val repositoryImpl : ProductDatabaseRepositoryImpl) {
    suspend operator fun invoke(productId : Int) = repositoryImpl.deleteProduct(productId)
}