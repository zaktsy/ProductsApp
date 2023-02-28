package com.zaktsy.products.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class StorageWithProducts (
    @Embedded
    val storage: StorageEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "storageId"
    )
    val products: List<ProductEntity>
)