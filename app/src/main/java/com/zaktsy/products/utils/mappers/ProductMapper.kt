package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.CategoryWithProducts
import com.zaktsy.products.data.local.entities.ProductEntity
import com.zaktsy.products.data.local.entities.ProductEntityWithCategoryAndStorage
import com.zaktsy.products.data.local.entities.StorageWithProducts
import com.zaktsy.products.domain.models.*

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

        fun transformToGroupedByCategory(products: List<CategoryWithProducts>): List<GroupedProducts> {
            val groupedProducts = ArrayList<GroupedProducts>()
            products.forEach {
                val productsList = ArrayList<Product>()
                it.products.forEach { product ->
                    productsList.add(transformFrom(product))
                }
                val groupedProduct =
                    GroupedProducts(ModelWithName(it.category.id, it.category.name), productsList)
                groupedProducts.add(groupedProduct)
            }

            return groupedProducts
        }

        fun transformToGroupedByStorage(products: List<StorageWithProducts>): List<GroupedProducts> {
            val groupedProducts = ArrayList<GroupedProducts>()
            products.forEach {
                val productsList = ArrayList<Product>()
                it.products.forEach { product ->
                    productsList.add(transformFrom(product))
                }
                val groupedProduct =
                    GroupedProducts(ModelWithName(it.storage.id, it.storage.name), productsList)
                groupedProducts.add(groupedProduct)
            }

            return groupedProducts
        }

        fun transformFrom(data: ProductEntity): Product {
            return Product(
                data.id,
                data.name,
                data.expirationDuration,
                data.barCode,
                null,
                null,
                data.manufactureDate,
            )
        }

        fun transformFrom(data: ProductEntityWithCategoryAndStorage): Product {
            return Product(
                data.product.id,
                data.product.name,
                data.product.expirationDuration,
                data.product.barCode,
                data.category?.let { Category(it.id, data.category.name) },
                data.storage?.let { Storage(it.id, data.storage.name) },
                data.product.manufactureDate,
            )
        }
    }
}