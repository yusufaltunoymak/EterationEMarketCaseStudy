package com.altunoymak.eterationemarketcasestudy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(product: FavoriteProduct)

    @Query("DELETE FROM favorite_products WHERE productId = :productId")
    suspend fun removeFavoriteProduct(productId: String)

    @Query("SELECT * FROM favorite_products WHERE productId = :productId")
    suspend fun getFavoriteProduct(productId: String): FavoriteProduct?

    @Query("SELECT * FROM favorite_products")
    fun getFavoriteProducts(): Flow<List<FavoriteProduct>>
}