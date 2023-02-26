package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.ProductEntity
import com.zaktsy.products.domain.models.Product

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
    }
}