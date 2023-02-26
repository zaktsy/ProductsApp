package com.zaktsy.products.domain.repository

import com.zaktsy.products.data.local.ProductsDao
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.models.Storage
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
        val storageEntity = StorageMapper.transformTo(storage)
        productsDao.deleteStorage(storageEntity)
    }

    suspend fun editStorage(storage: Storage) {
        val storageEntity = StorageMapper.transformTo(storage)
        productsDao.updateStorage(storageEntity)
    }
    //endregion

    //region Products
    suspend fun addProduct(product: Product) {
        val productEntity = ProductMapper.transformTo(product)
        productsDao.addProduct(productEntity)
    }
    //endregion
}