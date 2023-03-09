package com.zaktsy.products.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

class ProductTemplateWithCategory(
    @Embedded
    val productTemplate: ProductTemplateEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    ) val category: CategoryEntity?,
)