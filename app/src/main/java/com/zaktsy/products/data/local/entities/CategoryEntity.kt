package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    val name: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}