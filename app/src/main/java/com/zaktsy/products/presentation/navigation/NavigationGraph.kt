package com.zaktsy.products.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.zaktsy.products.presentation.screens.AddProductScreen
import com.zaktsy.products.presentation.screens.ProductsScreen
import com.zaktsy.products.presentation.screens.ShoppingListScreen

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = NavigationRoute.Products.path
    ) {
        composable(BottomNavigationScreens.Products.route) {
            ProductsScreen(navigateToAddScreen = {
                navController.navigate(NavigationRoute.ShoppingList.path)
            })
        }

        composable(BottomNavigationScreens.ShoppingList.route) {
            ShoppingListScreen()
        }

        addAddProductScreen(navController, this)
    }
}

private fun addAddProductScreen(
    navController: NavHostController, navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavigationRoute.AddProduct.path) {
        AddProductScreen()
    }
}