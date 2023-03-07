package com.zaktsy.products.domain.repository

import com.zaktsy.products.data.local.ProductsDao
import com.zaktsy.products.data.local.entities.ProductEntity
import com.zaktsy.products.domain.models.*
import com.zaktsy.products.utils.mappers.AlarmMapper
import com.zaktsy.products.utils.mappers.CategoryMapper
import com.zaktsy.products.utils.mappers.ProductMapper
import com.zaktsy.products.utils.mappers.StorageMapper
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDao: ProductsDao){

    //region categories
    suspend fun getAllCategories(): List<Category> {
        val categoryEntities = productsDao.getAllCategories()
        return CategoryMapper.transformToCategories(categoryEntities)
    }

    suspend fun getCategories(name: String): List<Category>{
        val categoryEntities = productsDao.getCategories(name)
        return CategoryMapper.transformToCategories(categoryEntities)
    }

    suspend fun addCategory(category: Category) {
        val categoryEntity = CategoryMapper.transformTo(category)
        productsDao.addCategory(categoryEntity)
    }

    suspend fun deleteCategory(category: Category) {
        val productsWithCategory = getProductsByCategory(category.id)
        productsWithCategory.forEach(){
            it.categoryId = null
        }
        productsDao.updateProducts(productsWithCategory)
        val categoryEntity = CategoryMapper.transformTo(category)
        productsDao.deleteCategory(categoryEntity)
    }

    suspend fun editCategory(category: Category) {
        val categoryEntity = CategoryMapper.transformTo(category)
        productsDao.updateCategory(categoryEntity)
    }
    //endregion

    //region storages
    suspend fun getAllStorages(): List<Storage> {
        val storageEntities = productsDao.getAllStorages()
        return StorageMapper.transformToStorages(storageEntities)
    }

    suspend fun getStorages(name: String): List<Storage>{
        val storageEntities = productsDao.getStorages(name)
        return StorageMapper.transformToStorages(storageEntities)
    }

    suspend fun addStorage(storage: Storage) {
        val storageEntity = StorageMapper.transformTo(storage)
        productsDao.addStorage(storageEntity)
    }

    suspend fun deleteStorage(storage: Storage) {
        val productsWithStorage = getProductsByStorage(storage.id)
        productsWithStorage.forEach(){
            it.storageId = null
        }
        productsDao.updateProducts(productsWithStorage)
        val storageEntity = StorageMapper.transformTo(storage)
        productsDao.deleteStorage(storageEntity)
    }

    suspend fun editStorage(storage: Storage) {
        val storageEntity = StorageMapper.transformTo(storage)
        productsDao.updateStorage(storageEntity)
    }
    //endregion

    //region Products
    suspend fun getProduct(productId: Long): Product {
        val product = productsDao.getProduct(productId)
        return ProductMapper.transformFrom(product)
    }

    suspend fun addProduct(product: Product):Long {
        val productEntity = ProductMapper.transformTo(product)
        return productsDao.addProduct(productEntity)
    }

    suspend fun getProductsByCategory(name: String): List<Product> {
        val products = productsDao.getProducts(name)
        return ProductMapper.transformToProducts(products)
    }

    suspend fun getProductsGropedByCategory(name: String): List<GroupedProducts> {
        val products = productsDao.getCategoriesWithProducts(name)
        return ProductMapper.transformToGroupedByCategory(products)
    }

    suspend fun getProductsGropedByStorage(name: String): List<GroupedProducts> {
        val products = productsDao.getStoragesWithProducts(name)
        return ProductMapper.transformToGroupedByStorage(products)
    }

    private suspend fun getProductsByCategory(categoryId: Long): List<ProductEntity> {
        return productsDao.getProductsByCategory(categoryId)
    }

    private suspend fun getProductsByStorage(storageId: Long): List<ProductEntity> {
        return productsDao.getProductsByStorage(storageId)
    }

    suspend fun editProduct(product: Product) {
        val productEntity = ProductMapper.transformTo(product)
        productsDao.updateProduct(productEntity)
    }

    suspend fun removeProduct(product: Product) {
        val productEntity = ProductMapper.transformTo(product)
        productsDao.deleteProduct(productEntity)
    }
    //endregion

    //region alarms
    suspend fun addAlarm(alarm: ExpirationAlarm): Long {
        val alarmEntity = AlarmMapper.transformTo(alarm)
        return productsDao.addAlarm(alarmEntity)
    }
    //endregion
}