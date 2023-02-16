package com.zaktsy.products.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storages")
data class StorageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)