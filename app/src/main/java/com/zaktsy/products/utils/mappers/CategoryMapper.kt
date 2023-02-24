package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.CategoryEntity
import com.zaktsy.products.domain.models.Category

class CategoryMapper {
    companion object {
        fun transformFrom(data: CategoryEntity): Category {
            return Category(data.id, data.name)
        }

        fun transformTo(data: Category): CategoryEntity {
            val category = CategoryEntity(data.name)
            category.id = data.id

            return category
        }

        fun transformToCategories(categoryEntities: List<CategoryEntity>): List<Category> {
            val categories = ArrayList<Category>()

            categoryEntities.forEach() {
                val category = transformFrom(it)
                categories.add(category)
            }

            return categories
        }
    }
}