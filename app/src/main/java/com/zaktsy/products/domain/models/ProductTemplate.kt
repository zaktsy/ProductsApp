package com.zaktsy.products.domain.models

class ProductTemplate(
    var id: Long = 0,
    var name: String,
    var expirationDuration: Long,
    var barCode: String,
    var category: Category?,
)