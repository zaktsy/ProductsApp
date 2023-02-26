package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list",
    foreignKeys = [ForeignKey(
        entity = ProductTemplateEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("productTemplateId")
    )])
data class ShoppingListItemEntity(
    val productTemplateId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}