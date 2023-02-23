package com.zaktsy.products.domain.usecases.categories

import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class EditCategoryUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(category: Category) = repository.editCategory(category)
}