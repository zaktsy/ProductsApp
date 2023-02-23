package com.zaktsy.products.domain.models

data class Product(
    var name: String,
    var expirationDuration: Long,
    var barCode: String,
    val categoryId: Long
)