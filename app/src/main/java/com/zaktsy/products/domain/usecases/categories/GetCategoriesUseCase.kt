package com.zaktsy.products.domain.usecases.categories

import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: ProductsRepository){
    suspend operator fun invoke(name:String): List<Category>{
        if (name == "")
            return repository.getAllCategories()

        return repository.getCategories(name)
    }
}