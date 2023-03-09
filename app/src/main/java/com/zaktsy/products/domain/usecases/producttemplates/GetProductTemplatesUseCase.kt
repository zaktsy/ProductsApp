package com.zaktsy.products.domain.usecases.producttemplates

import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductTemplatesUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(name: String) = repository.getProductTemplates(name)
}