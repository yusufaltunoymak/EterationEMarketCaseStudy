package com.altunoymak.eterationemarketcasestudy.domain.di

import android.content.Context
import androidx.room.Room
import com.altunoymak.eterationemarketcasestudy.data.local.ProductDao
import com.altunoymak.eterationemarketcasestudy.data.local.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideProductsDatabase(@ApplicationContext context : Context) : ProductDatabase {
        return Room.databaseBuilder(context, ProductDatabase::class.java,"product_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }
}