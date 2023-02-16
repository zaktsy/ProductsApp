package com.zaktsy.products.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.zaktsy.products.presentation.navigation.BottomNavigationBar
import com.zaktsy.products.presentation.navigation.NavigationGraph
import com.zaktsy.products.ui.theme.ProductsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
private fun MainScreen() {
    ProductsTheme {
        val navController = rememberNavController()
        Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) {

            NavigationGraph(navController = navController)
        }
    }
}