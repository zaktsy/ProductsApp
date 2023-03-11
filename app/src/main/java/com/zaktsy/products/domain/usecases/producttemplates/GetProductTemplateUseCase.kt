package com.zaktsy.products.domain.usecases.producttemplates

import com.zaktsy.products.domain.models.ProductTemplate
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductTemplateUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(productTemplateId: Long): ProductTemplate = repository.getProductTemplate(productTemplateId)
    suspend operator fun invoke(barcode: String): ProductTemplate = repository.getProductTemplate(barcode)
}