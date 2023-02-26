package com.zaktsy.products.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.zaktsy.products.domain.models.Storage

data class StorageWithProducts (
    @Embedded
    val storage: Storage,
    @Relation(
        parentColumn = "id",
        entityColumn = "storageId"
    )
    val products: List<ProductEntity>
)