package com.zaktsy.products.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts(
    @Embedded
    val storage: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<ProductEntity>
)