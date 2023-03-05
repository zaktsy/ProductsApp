package com.zaktsy.products.domain.usecases.products

import com.zaktsy.products.domain.models.GroupedProducts
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.repository.ProductsRepository
import com.zaktsy.products.utils.ProductsSortOrder
import java.util.*
import javax.inject.Inject

class GetProductsGropedByStorageUseCase @Inject constructor(private val repository: ProductsRepository) {

    suspend operator fun invoke(sortOrder: ProductsSortOrder, name: String): List<GroupedProducts> {
        val groups = repository.getProductsGropedByStorage(name)

        when (sortOrder) {

            ProductsSortOrder.ALPHABETICALLY -> {
                groups.forEach { groupedProducts ->
                    val sortedProducts = groupedProducts.products.sortedBy {
                        it.name
                    }
                    groupedProducts.products.clear()
                    groupedProducts.products.addAll(sortedProducts)
                }
            }
            ProductsSortOrder.EXPIRATION -> {
                groups.forEach { groupedProducts ->
                    val sortedProducts = groupedProducts.products.sortedBy {
                        Date().time - (it.manufactureDate.time + it.expirationDuration)
                    }
                    groupedProducts.products.clear()
                    groupedProducts.products.addAll(sortedProducts)
                }
            }
        }

        return groups
    }
}