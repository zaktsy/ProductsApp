package com.zaktsy.products.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id", entityColumn = "categoryId", entity = ProductEntity::class
    ) val playlists: List<ProductInStorageWithInfo>
)