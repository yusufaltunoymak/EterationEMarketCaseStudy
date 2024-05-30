package com.altunoymak.eterationemarketcasestudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.altunoymak.eterationemarketcasestudy.data.local.model.FavoriteProduct
import com.altunoymak.eterationemarketcasestudy.data.local.model.Order
import com.altunoymak.eterationemarketcasestudy.data.local.model.Product

@Database(entities = [Product::class,FavoriteProduct::class, Order::class], version = 6, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun favoriteProductDao(): FavoriteProductDao
    abstract fun orderDao(): OrderDao
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
    }
}