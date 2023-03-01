package com.zaktsy.products.domain.models

class GroupedProducts(
    val groupingModel: ModelWithName, val products: List<Product>
)
