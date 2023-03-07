package com.zaktsy.products.data.local

import androidx.room.*
import com.zaktsy.products.data.local.entities.*

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

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProduct(productId: Long): ProductEntityWithCategoryAndStorage

    @Insert
    suspend fun addProduct(productEntity: ProductEntity): Long

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    suspend fun getProductsByCategory(categoryId: Long): List<ProductEntity>

    @Query("SELECT * FROM products WHERE storageId = :storageId")
    suspend fun getProductsByStorage(storageId: Long): List<ProductEntity>

    @Update
    suspend fun updateProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Transaction
    @Query("SELECT * FROM products WHERE productName LIKE '%' || :name || '%'")
    suspend fun getProducts(name: String): List<ProductEntityWithCategoryAndStorage>

    @Query("SELECT * FROM products WHERE productName LIKE '%' || :name || '%' and categoryId = :categoryId")
    suspend fun getProductsSortedByCategory(categoryId: Long, name: String): List<ProductEntity>

    @Transaction
    @Query("")
    suspend fun getCategoriesWithProducts(name: String): List<CategoryWithProducts> {
        val rv = arrayListOf<CategoryWithProducts>()
        for (category in getAllCategories()) {
            rv.add(
                CategoryWithProducts(
                    category, this.getProductsSortedByCategory(category.id, name)
                )
            )
        }
        return rv
    }

    @Query("SELECT * FROM products WHERE productName LIKE '%' || :name || '%' and storageId = :storageId")
    suspend fun getProductsSortedByStorage(storageId: Long, name: String): List<ProductEntity>

    @Transaction
    @Query("")
    suspend fun getStoragesWithProducts(name: String): List<StorageWithProducts> {
        val rv = arrayListOf<StorageWithProducts>()
        for (storage in getAllStorages()) {
            rv.add(
                StorageWithProducts(
                    storage, this.getProductsSortedByStorage(storage.id, name)
                )
            )
        }
        return rv
    }
    //endregion

    //region alarms

    @Insert
    suspend fun addAlarm(alarmEntity: ExpirationAlarmEntity): Long
    //endregion
}