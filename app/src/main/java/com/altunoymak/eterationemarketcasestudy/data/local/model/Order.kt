package com.altunoymak.eterationemarketcasestudy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val productId: String,
    val name: String,
    val image: String,
    val price: String,
    val quantity: Int = 1,
    val orderDate: String,
    val totalPrice: String
)