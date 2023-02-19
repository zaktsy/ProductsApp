package com.zaktsy.products.presentation.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zaktsy.products.presentation.screens.AddProductScreen
import com.zaktsy.products.presentation.screens.ProductsScreen
import com.zaktsy.products.presentation.screens.SettingsScreen
import com.zaktsy.products.presentation.screens.ShoppingListScreen
import com.zaktsy.products.ui.theme.ProductsTheme

@Composable
fun BottomBarAnimationApp() {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val scrollState = rememberLazyListState()

    ProductsTheme {
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        when (navBackStackEntry?.destination?.route) {
            NavigationRoutes.Products -> {
                bottomBarState.value = true
            }
            NavigationRoutes.ShoppingList -> {
                bottomBarState.value = true
            }
            NavigationRoutes.AddProduct -> {
                bottomBarState.value = false
            }
            NavigationRoutes.Settings -> {
                bottomBarState.value = true
            }
        }

        Scaffold(modifier = Modifier.navigationBarsPadding(), bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarState = bottomBarState,
                scrollState = scrollState
            )
        }, content = {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.Products.route,
            ) {
                composable(NavigationItem.Products.route) {
                    ProductsScreen(
                        navController = navController, scrollState = scrollState
                    )
                }
                composable(NavigationItem.ShoppingList.route) {
                    ShoppingListScreen(
                        navController = navController
                    )
                }
                composable(NavigationItem.AddProduct.route) {
                    AddProductScreen(
                        navController = navController
                    )
                }
                composable(NavigationItem.Settings.route) {
                    SettingsScreen(
                        navController = navController
                    )
                }
            }
        })
    }
}