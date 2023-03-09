package com.zaktsy.products.domain.usecases.producttemplates

import com.zaktsy.products.domain.models.ProductTemplate
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class AddProductTemplateUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(productTemplate: ProductTemplate) = repository.addProductTemplate(productTemplate)
}