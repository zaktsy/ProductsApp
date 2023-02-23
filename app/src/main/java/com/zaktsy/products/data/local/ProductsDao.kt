package com.zaktsy.products.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zaktsy.products.data.local.entities.CategoryEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :name || '%'")
    suspend fun getCategories(name: String): List<CategoryEntity>;

    @Insert()
    suspend fun addCategory(categoryEntity: CategoryEntity)
}