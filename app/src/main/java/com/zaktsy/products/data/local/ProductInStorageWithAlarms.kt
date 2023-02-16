package com.zaktsy.products.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class ProductInStorageWithAlarms(
    @Embedded val productInfo: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productInStorageId"
    )
    val playlists: List<ExpirationAlarmEntity>
)