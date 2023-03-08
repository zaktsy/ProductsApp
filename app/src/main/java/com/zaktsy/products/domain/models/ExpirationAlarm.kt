package com.zaktsy.products.domain.models

import java.util.Date

class ExpirationAlarm(
    var id: Long = 0,
    val productId: Long,
    val daysToExpiration: Int,
    val dayToNotify: Date
)