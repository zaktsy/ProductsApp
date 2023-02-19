package com.zaktsy.products.domain.repository

import com.zaktsy.products.data.local.ProductsDao
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDao: ProductsDao){

}