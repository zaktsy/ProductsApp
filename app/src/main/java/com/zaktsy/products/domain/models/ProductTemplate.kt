package com.zaktsy.products.domain.models

class ProductTemplate(
    var id: Long = 0,
    val name: String,
    val expirationDuration: Long,
    val barCode: Long,
    var category: Category?,
)