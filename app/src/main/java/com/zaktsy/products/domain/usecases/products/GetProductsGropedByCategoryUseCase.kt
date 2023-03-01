package com.zaktsy.products.domain.usecases.products

import com.zaktsy.products.domain.models.GroupedProducts
import com.zaktsy.products.domain.repository.ProductsRepository
import com.zaktsy.products.utils.ProductsSortOrder
import java.util.*
import javax.inject.Inject

class GetProductsGropedByCategoryUseCase @Inject constructor(private val repository: ProductsRepository) {

    suspend operator fun invoke(sortOrder: ProductsSortOrder, name: String): List<GroupedProducts> {
        val products = repository.getProductsGropedByCategory(name)

        when (sortOrder) {

            ProductsSortOrder.ALPHABETICALLY -> {
                products.forEach{ groupedProducts ->
                    groupedProducts.products.sortedBy {
                        it.name
                    }
                }
            }
            ProductsSortOrder.EXPIRATION ->{
                products.forEach{ groupedProducts ->
                    groupedProducts.products.sortedBy {
                        Date().time - (it.manufactureDate.time + it.expirationDuration)
                    }
                }
            }
        }

        return products
    }
}