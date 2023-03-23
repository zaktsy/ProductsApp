package com.zaktsy.products.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zaktsy.products.presentation.screens.addproduct.AddProductScreen
import com.zaktsy.products.presentation.screens.barcodescanner.BarcodeScannerScreen
import com.zaktsy.products.presentation.screens.categories.CategoriesScreen
import com.zaktsy.products.presentation.screens.editproduct.EditProductScreen
import com.zaktsy.products.presentation.screens.edittemplate.EditTemplateScreen
import com.zaktsy.products.presentation.screens.products.ProductsScreen
import com.zaktsy.products.presentation.screens.settings.SettingsScreen
import com.zaktsy.products.presentation.screens.shoppinglist.ShoppingListScreen
import com.zaktsy.products.presentation.screens.storages.StoragesScreen
import com.zaktsy.products.presentation.screens.templates.TemplatesScreen
import com.zaktsy.products.ui.components.BottomBar
import com.zaktsy.products.ui.theme.ProductsTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarAnimationApp() {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val needToUpdateProducts = rememberSaveable { mutableStateOf(false) }
    val needToUpdateTemplates = rememberSaveable { mutableStateOf(false) }
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
            NavigationRoutes.Categories -> {
                bottomBarState.value = false
            }
            NavigationRoutes.Storages -> {
                bottomBarState.value = false
            }
            NavigationRoutes.EditProduct -> {
                bottomBarState.value = false
            }
            NavigationRoutes.ProductTemplates -> {
                bottomBarState.value = true
            }
            NavigationRoutes.EditTemplate -> {
                bottomBarState.value = false
            }
            NavigationRoutes.BarcodeScanner -> {
                bottomBarState.value = false
            }
        }

        Scaffold(modifier = Modifier.navigationBarsPadding(),
            backgroundColor = MaterialTheme.colorScheme.background,
            bottomBar = {
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
                        navController = navController,
                        scrollState = scrollState,
                        needToUpdateProducts = needToUpdateProducts,
                    )
                }
                composable(NavigationItem.ShoppingList.route) {
                    ShoppingListScreen(navController = navController)
                }
                composable(NavigationItem.AddProduct.route) {
                    AddProductScreen(
                        navController = navController,
                        needToUpdateProducts = needToUpdateProducts,
                        needToUpdateTemplates = needToUpdateTemplates
                    )
                }
                composable(NavigationItem.Settings.route) {
                    SettingsScreen(navController = navController)
                }
                composable(NavigationItem.Categories.route) {
                    CategoriesScreen(
                        navController = navController,
                        scrollState = scrollState,
                        needToUpdateProducts = needToUpdateProducts,
                        needToUpdateTemplates = needToUpdateTemplates
                    )
                }
                composable(NavigationItem.Storages.route) {
                    StoragesScreen(
                        navController = navController,
                        scrollState = scrollState,
                        needToUpdateProducts = needToUpdateProducts,
                        needToUpdateTemplates = needToUpdateTemplates
                    )
                }
                composable(NavigationItem.EditProduct.route) {
                    EditProductScreen(
                        navController = navController, needToUpdateProducts = needToUpdateProducts
                    )
                }
                composable(NavigationItem.ProductTemplates.route) {
                    TemplatesScreen(
                        navController = navController,
                        scrollState = scrollState,
                        needToUpdateTemplates = needToUpdateTemplates
                    )
                }
                composable(NavigationItem.EditTemplate.route) {
                    EditTemplateScreen(
                        navController = navController, needToUpdateTemplates = needToUpdateTemplates
                    )
                }
                composable(NavigationItem.BarcodeScanner.route) {
                    BarcodeScannerScreen(navController = navController)
                }
                composable(NavigationItem.AddProductWithBarCode.route) {
                    AddProductScreen(
                        navController = navController,
                        needToUpdateProducts = needToUpdateProducts,
                        needToUpdateTemplates = needToUpdateTemplates
                    )
                }
            }
        })
    }
}