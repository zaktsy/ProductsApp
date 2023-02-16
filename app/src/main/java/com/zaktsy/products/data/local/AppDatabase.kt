package com.zaktsy.products.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaktsy.products.data.local.entities.*

@Database(
    entities = [CategoryEntity::class, ExpirationAlarmEntity::class, ProductEntity::class, ProductInStorageEntity::class, StorageEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao
}