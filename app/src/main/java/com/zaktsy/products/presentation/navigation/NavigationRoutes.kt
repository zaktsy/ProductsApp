package com.zaktsy.products.presentation.navigation

sealed class NavigationRoutes {
    companion object {
        const val Products = "products"

        const val ShoppingList = "shopping_list"

        const val AddProduct = "add_product"

        const val Settings = "settings"

        const val Categories = "categories"

        const val Storages = "storages"

        const val EditProductRoot = "edit_product"
        const val EditProductArg = "productId"
        const val EditProduct = "$EditProductRoot/{$EditProductArg}"

        const val EditTemplateRoot = "edit_template"
        const val EditTemplateArg = "templateId"
        const val EditTemplate = "$EditTemplateRoot/{$EditTemplateArg}"

        const val ProductTemplates = "product_templates"
    }
}
