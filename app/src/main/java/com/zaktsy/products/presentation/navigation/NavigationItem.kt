package com.zaktsy.products.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.zaktsy.products.R

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: Int) {
    object Products : NavigationItem(NavigationRoutes.Products, Icons.Default.Kitchen, R.string.products)
    object ShoppingList : NavigationItem(NavigationRoutes.ShoppingList, Icons.Default.ShoppingCart, R.string.shoppingList)
    object AddProduct : NavigationItem(NavigationRoutes.AddProduct, Icons.Default.Add, R.string.addProducts)
    object Settings: NavigationItem(NavigationRoutes.Settings, Icons.Default.Settings, R.string.settings)
    object Categories: NavigationItem(NavigationRoutes.Categories, Icons.Default.Category, R.string.categories)
    object Storages: NavigationItem(NavigationRoutes.Storages, Icons.Default.Storage, R.string.storages)
}
