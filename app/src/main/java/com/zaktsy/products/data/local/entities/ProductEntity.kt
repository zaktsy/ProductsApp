package com.zaktsy.products.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "products", foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId")
    ), ForeignKey(
        entity = StorageEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("storageId")
    )]
)
data class ProductEntity(
    val name: String,
    val expirationDuration: Long,
    val barCode: String,
    @ColumnInfo(index = true)
    val categoryId: Long?,
    @ColumnInfo(index = true)
    val storageId: Long?,
    val manufactureDate: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}