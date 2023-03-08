package com.zaktsy.products.domain.alarms

import com.zaktsy.products.domain.models.ExpirationAlarm

interface AlarmScheduler {
    suspend fun schedule(alarm: ExpirationAlarm)
    fun cancel(alarm: ExpirationAlarm)
}