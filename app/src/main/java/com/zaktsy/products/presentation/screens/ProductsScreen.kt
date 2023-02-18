package com.zaktsy.products.presentation.screens

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.zaktsy.products.presentation.navigation.NavigationRoutes

@Composable
fun ProductsScreen(
    navController: NavController
) {
    Scaffold {
        FloatingActionButton(onClick = { navController.navigate(NavigationRoutes.AddProduct) }) {
            
        }
    }
}