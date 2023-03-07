package com.zaktsy.products.domain.models

import java.util.Date

class ExpirationAlarm(
    val id: Long = 0,
    val productId: Long,
    val daysToExpiration: Int,
    val dayToNotify: Date
)