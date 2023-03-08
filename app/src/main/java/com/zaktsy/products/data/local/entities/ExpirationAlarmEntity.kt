package com.zaktsy.products.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "expiration_alarms", foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("productId"),
        onDelete = CASCADE
    )]
)
data class ExpirationAlarmEntity(
    @ColumnInfo(index = true)
    val productId: Long,
    val daysToExpiration: Int,
    val dayToNotify: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}