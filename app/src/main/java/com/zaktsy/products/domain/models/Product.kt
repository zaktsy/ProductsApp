package com.zaktsy.products.domain.models

import java.util.Date

data class Product(
    val id: Long = 0,
    var name: String,
    var expirationDuration: Long,
    var barCode: String,
    val category: Category?,
    val storage: Storage?,
    var manufactureDate: Date
)