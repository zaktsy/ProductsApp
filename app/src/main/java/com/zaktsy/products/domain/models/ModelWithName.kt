package com.zaktsy.products.domain.models

open class ModelWithName {
    open var id: Long = 0
    open var name: String

    constructor(name: String) {
        name.also { this.name = it }
    }

    constructor(id: Long, name: String) : this(name) {
        id.also { this.id = it }
    }
}