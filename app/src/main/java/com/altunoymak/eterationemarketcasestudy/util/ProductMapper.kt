package com.altunoymak.eterationemarketcasestudy.util

import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import com.altunoymak.eterationemarketcasestudy.data.remote.model.ProductResponseItem

fun convertResponseItemToEntity(productResponseItem: ProductResponseItem): Product {
    return Product(
        productId = productResponseItem.id ?: "",
        name = productResponseItem.name ?: "",
        image = productResponseItem.image ?: "",
        price = productResponseItem.price ?: "",
        brand = productResponseItem.brand ?: "",
        createdAt = productResponseItem.createdAt ?: "",
        description = productResponseItem.description ?: "",
        model = productResponseItem.model ?: ""
    )
}