package com.zaktsy.products.data.local

import androidx.room.*
import com.zaktsy.products.data.local.entities.CategoryEntity
import com.zaktsy.products.data.local.entities.ProductEntity
import com.zaktsy.products.data.local.entities.StorageEntity

@Dao
interface ProductsDao {

    //region categories
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :name || '%'")
    suspend fun getCategories(name: String): List<CategoryEntity>

    @Insert
    suspend fun addCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)
    //endregion

    //region storages
    @Query("SELECT * FROM storages")
    suspend fun getAllStorages(): List<StorageEntity>

    @Query("SELECT * FROM storages WHERE name LIKE '%' || :name || '%'")
    suspend fun getStorages(name: String): List<StorageEntity>

    @Insert
    suspend fun addStorage(storageEntity: StorageEntity)

    @Delete
    suspend fun deleteStorage(storageEntity: StorageEntity)

    @Update
    suspend fun updateStorage(storageEntity: StorageEntity)
    //endregion

    //region products
    @Insert
    suspend fun addProduct(productEntity: ProductEntity)
}