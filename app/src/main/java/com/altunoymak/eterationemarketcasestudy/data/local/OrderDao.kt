package com.altunoymak.eterationemarketcasestudy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM order_table")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM order_table WHERE productId = :productId LIMIT 1")
    suspend fun getOrder(productId: String): Order?
}