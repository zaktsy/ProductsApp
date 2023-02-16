package com.zaktsy.products.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class StorageWithProducts(
    @Embedded val storage: StorageEntity,
    @Relation(
        parentColumn = "id", entityColumn = "storageId", entity = ProductEntity::class
    ) val playlists: List<ProductInStorageWithInfo>
)