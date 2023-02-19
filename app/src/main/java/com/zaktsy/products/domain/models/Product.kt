package com.zaktsy.products.domain.models

data class Product(
    val name: String,
    val expirationDuration: Long,
    val barCode: String,
    val categoryId: Long
) {}