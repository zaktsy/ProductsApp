package com.zaktsy.products.domain.models

class Storage(
    var name: String
) {
    var id: Long = 0

    constructor(id: Long, name: String) : this(name) {
        this.id = id
    }
}