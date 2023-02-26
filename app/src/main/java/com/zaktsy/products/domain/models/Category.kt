package com.zaktsy.products.domain.models

class Category : ModelWithName {
    override var id: Long = 0
    override lateinit var name: String
    constructor(name: String) : super(name = name)
    constructor(id: Long, name: String) : super(id = id, name = name)
}