package com.altunoymak.eterationemarketcasestudy.util

import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
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

fun mapToFavorite(product: Product, isFavorite: Boolean): FavoriteProduct {
    return FavoriteProduct(
        id = 0,
        productId = product.productId,
        name = product.name,
        image = product.image,
        price = product.price,
        brand = product.brand ?: "",
        createdAt = product.createdAt?: "",
        description = product.description?: "",
        model = product.model?: "",
        isFavorite = isFavorite
    )
}