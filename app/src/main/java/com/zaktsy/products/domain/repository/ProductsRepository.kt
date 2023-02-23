package com.zaktsy.products.domain.repository

import com.zaktsy.products.data.local.ProductsDao
import com.zaktsy.products.data.local.entities.CategoryEntity
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.utils.mappers.CategoryMapper
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDao: ProductsDao){

    suspend fun getAllCategories(): List<Category>{
        val categoryEntities = productsDao.getAllCategories()
        val categories = ArrayList<Category>()

        categoryEntities.forEach(){
           val category =  CategoryMapper.transformFrom(it)
            categories.add(category)
        }

        return categories
    }

    suspend fun addCategory(category: Category) {
        val categoryEntity = CategoryMapper.transformTo(category)
        if (categoryEntity != null) {
            productsDao.addCategory(categoryEntity)
        }
    }
}