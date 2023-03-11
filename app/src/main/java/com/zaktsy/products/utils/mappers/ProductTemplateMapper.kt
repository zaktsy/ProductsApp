package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.ProductTemplateEntity
import com.zaktsy.products.data.local.entities.ProductTemplateWithCategory
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.ProductTemplate

class ProductTemplateMapper {

    companion object {

        fun transformFrom(data: ProductTemplateWithCategory): ProductTemplate {
            return ProductTemplate(data.productTemplate.id,
                data.productTemplate.name,
                data.productTemplate.expirationDuration,
                data.productTemplate.barCode,
                data.category?.let { Category(it.id, data.category.name) })
        }

        fun transformTo(data: ProductTemplate): ProductTemplateEntity {
            val productTemplateEntity = ProductTemplateEntity(
                data.name,
                data.expirationDuration,
                data.barCode,
                data.category?.id,
            )
            productTemplateEntity.id = data.id
            return productTemplateEntity
        }

        fun transformToProductTemplates(productTemplateEntities: List<ProductTemplateWithCategory>): List<ProductTemplate> {
            val productTemplates = ArrayList<ProductTemplate>()

            productTemplateEntities.forEach {
                val productTemplate = transformFrom(it)
                productTemplates.add(productTemplate)
            }

            return productTemplates
        }
    }
}