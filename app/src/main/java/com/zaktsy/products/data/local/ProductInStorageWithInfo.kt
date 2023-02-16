package com.zaktsy.products.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class ProductInStorageWithInfo(
    @Embedded val productInfo: ProductEntity,
    @Relation(
        parentColumn = "id", entityColumn = "productId"
    ) val productInStorage: ProductInStorageEntity
)