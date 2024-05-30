package com.altunoymak.eterationemarketcasestudy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val productId: String,
    val name: String,
    val image: String,
    val price: String,
    val brand: String,
    val createdAt: String,
    val description: String,
    val model: String,
    val isFavorite: Boolean = false
)
