package com.zaktsy.products.domain.models

class GroupedProducts(
    val groupingModel: ModelWithName, var products: MutableList<Product>
)
