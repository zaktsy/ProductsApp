package com.zaktsy.products.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductEntityWithCategoryAndStorage(
    @Embedded
    val product: ProductEntity,

    @Relation(
        parentColumn = "storageId",
        entityColumn = "id"
    )
    val storage: StorageEntity?,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?,
)