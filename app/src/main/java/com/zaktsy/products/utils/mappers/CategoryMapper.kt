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
            if (data.id != null) category.id = data.id!!

            return category
        }
    }
}