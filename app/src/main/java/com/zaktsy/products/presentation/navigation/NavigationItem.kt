package com.zaktsy.products.presentation.navigation

import com.zaktsy.products.R

sealed class NavigationItem(var route: String, var icon: Int, var title: Int) {
    object Products : NavigationItem(NavigationRoutes.Products, 1, R.string.products)
    object ShoppingList : NavigationItem(NavigationRoutes.ShoppingList, 1, R.string.shoppingList)
    object AddProduct : NavigationItem(NavigationRoutes.AddProduct, 1, R.string.addProducts)
}
