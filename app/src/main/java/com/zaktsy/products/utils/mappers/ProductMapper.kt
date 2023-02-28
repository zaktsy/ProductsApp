package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.ProductEntity
import com.zaktsy.products.data.local.entities.ProductEntityWithCategoryAndStorage
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.models.Storage

class ProductMapper {
    companion object {

        fun transformTo(data: Product): ProductEntity {
            val product = ProductEntity(
                data.name,
                data.expirationDuration,
                data.barCode,
                data.category?.id,
                data.storage?.id,
                data.manufactureDate
            )
            product.id = data.id

            return product
        }

        fun transformToProducts(productEntities: List<ProductEntityWithCategoryAndStorage>): List<Product> {
            val products = ArrayList<Product>()

            productEntities.forEach() {
                val product = transformFrom(it)
                products.add(product)
            }

            return products
        }

        private fun transformFrom(data: ProductEntityWithCategoryAndStorage): Product {
            return Product(
                data.product.id,
                data.product.name,
                data.product.expirationDuration,
                data.product.barCode,
                Category(data.category.id, data.category.name),
                Storage(data.storage.id, data.storage.name),
                data.product.manufactureDate,
            )
        }
    }
}