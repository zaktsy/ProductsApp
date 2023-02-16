package com.zaktsy.products.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ExpirationAlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val productInStorageId: Long,
    val alarmTime: Date,
)