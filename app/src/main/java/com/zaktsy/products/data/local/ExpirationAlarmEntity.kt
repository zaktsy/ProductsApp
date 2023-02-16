package com.zaktsy.products.data.local

import androidx.room.PrimaryKey
import java.util.Date

data class ExpirationAlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val productInStorageId: Long,
    val alarmTime: Date,
)