package com.zaktsy.products.data.local

import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDao: ProductsDao){

}