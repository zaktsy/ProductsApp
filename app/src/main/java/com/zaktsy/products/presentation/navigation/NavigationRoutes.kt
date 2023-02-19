package com.zaktsy.products.presentation.navigation

sealed class NavigationRoutes{
    companion object{
        const val Products = "products"
        const val ShoppingList = "shopping_list"
        const val AddProduct = "add_product"
        const val Settings = "settings"
    }
}
