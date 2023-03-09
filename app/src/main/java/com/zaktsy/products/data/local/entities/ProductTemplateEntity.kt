package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_templates")
class ProductTemplateEntity(
    val name: String,
    val expirationDuration: Long,
    val barCode: String,
    val categoryId: Long?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}