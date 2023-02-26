package com.zaktsy.products.di

import android.content.Context
import androidx.room.Room
import com.zaktsy.products.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDate(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "products_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.getProductsDao()
}