package com.zaktsy.products.domain.usecases.products

import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(productId: Long) = repository.getProduct(productId)
}