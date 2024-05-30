package com.altunoymak.eterationemarketcasestudy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val productId: String,
    val name: String,
    val image: String,
    val price: String,
    val brand: String?,
    val createdAt: String?,
    val description: String?,
    val model: String?,
    val quantity: Int = 1
)