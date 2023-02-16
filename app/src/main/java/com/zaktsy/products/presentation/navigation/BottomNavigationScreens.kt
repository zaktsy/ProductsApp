package com.zaktsy.products.presentation.navigation

import androidx.annotation.StringRes
import com.zaktsy.products.R

sealed class BottomNavigationScreens (val route: String, @StringRes val title: Int) {
    object Products : BottomNavigationScreens("products", R.string.products)
    object ShoppingList : BottomNavigationScreens("shoppingList", R.string.shoppingList)
}