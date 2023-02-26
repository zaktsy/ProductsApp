package com.zaktsy.products.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id", entityColumn = "categoryId", entity = ProductEntity::class
    ) val products: List<ProductInStorageWithInfo>
)