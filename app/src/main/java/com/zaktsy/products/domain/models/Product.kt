package com.zaktsy.products.domain.models

data class Product(
    val id: Long,
    var name: String,
    var expirationDuration: Long,
    var barCode: String?,
    val category: Category?,
    val storage: Storage?
)