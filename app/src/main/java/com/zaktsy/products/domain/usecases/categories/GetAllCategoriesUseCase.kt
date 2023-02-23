package com.zaktsy.products.domain.usecases.categories

import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val repository: ProductsRepository){
    suspend operator fun invoke() = repository.getAllCategories()
}