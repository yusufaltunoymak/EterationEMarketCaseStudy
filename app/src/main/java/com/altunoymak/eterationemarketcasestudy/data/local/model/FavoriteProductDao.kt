package com.altunoymak.eterationemarketcasestudy.data.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(product: FavoriteProduct)

    @Query("DELETE FROM favorite_products WHERE productId = :productId")
    suspend fun removeFavoriteProduct(productId: String)

    @Query("SELECT * FROM favorite_products WHERE productId = :productId")
    suspend fun getFavoriteProduct(productId: String): FavoriteProduct?
}