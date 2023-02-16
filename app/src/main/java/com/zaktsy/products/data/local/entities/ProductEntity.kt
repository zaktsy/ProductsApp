package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val expirationDuration: Long,
    val barCode: String,
    val categoryId: Long
)