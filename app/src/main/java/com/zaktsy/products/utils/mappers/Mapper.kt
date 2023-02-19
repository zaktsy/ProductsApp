package com.zaktsy.products.utils.mappers

interface Mapper<T,I> {

    fun transformFrom(data: T): I

    fun transformTo(data: I): T
}