package com.zaktsy.products.domain.models

class Category(
    var name: String
) {
    var id: Long? = null

    constructor(id: Long?, name: String) : this(name) {
        this.id = id
    }
}