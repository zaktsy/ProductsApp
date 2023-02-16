package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "products_in_storages")
data class ProductInStorageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val storageId: Long,
    val manufactureDate: Date
)