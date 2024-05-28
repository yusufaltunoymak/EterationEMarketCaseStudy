package com.altunoymak.eterationemarketcasestudy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("DELETE FROM product_table WHERE id = :id")
    suspend fun deleteProduct(id: Int)

    @Query("UPDATE product_table SET quantity = :quantity WHERE id = :id")
    suspend fun updateProductQuantity(id: Int, quantity: Int)
}