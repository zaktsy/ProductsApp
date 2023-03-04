package com.zaktsy.products.domain.usecases.products

import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.repository.ProductsRepository
import com.zaktsy.products.utils.ProductsSortOrder
import java.util.*
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(sortOrder: ProductsSortOrder, name: String): List<Product> {
        var products = repository.getProductsByCategory(name)

        products = when (sortOrder) {

            ProductsSortOrder.ALPHABETICALLY -> products.sortedBy {
                it.name
            }
            ProductsSortOrder.EXPIRATION -> products.sortedByDescending {
                Date().time - (it.manufactureDate.time + it.expirationDuration)
            }
        }

        return products
    }
}