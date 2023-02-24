package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storages")
data class StorageEntity(
    val name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}