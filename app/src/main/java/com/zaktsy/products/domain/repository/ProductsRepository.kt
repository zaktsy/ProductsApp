package com.zaktsy.products.domain.repository

import com.zaktsy.products.data.local.ProductsDao
import com.zaktsy.products.data.local.entities.CategoryEntity
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.utils.mappers.CategoryMapper
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDao: ProductsDao){

    suspend fun getAllCategories(): List<Category> {
        val categoryEntities = productsDao.getAllCategories()
        return transformToCategories(categoryEntities)
    }

    suspend fun getCategories(name: String): List<Category>{
        val categoryEntities = productsDao.getCategories(name)
        return transformToCategories(categoryEntities)
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

    private fun transformToCategories(categoryEntities: List<CategoryEntity>): List<Category>{
        val categories = ArrayList<Category>()

        categoryEntities.forEach(){
            val category =  CategoryMapper.transformFrom(it)
            categories.add(category)
        }

        return categories
    }
}