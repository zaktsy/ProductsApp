package com.zaktsy.products.domain.usecases.products

import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class EditProductUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(product: Product) = repository.editProduct(product)

}