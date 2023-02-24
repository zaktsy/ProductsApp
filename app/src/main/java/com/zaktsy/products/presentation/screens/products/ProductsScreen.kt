package com.zaktsy.products.presentation.screens.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.ExpandableSearch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navController: NavController, scrollState: LazyListState
) {

    val viewModel = hiltViewModel<ProductsViewModel>()
    val selectedDisplayMode = viewModel.selectedDisplayMode.collectAsState()
    val selectedSortOrder = viewModel.selectedSortOrder.collectAsState()

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 90.dp) { navController.navigate(NavigationRoutes.AddProduct) }
    }) {
        val myItems = mutableListOf<String>()
        repeat(40) {
            myItems.add("Item $it")
        }
        Column {
            LazyColumn(
                state = scrollState,
            ) {
                item {
                    ExpandableSearch(
                        false,
                        scrollState,
                        selectedDisplayMode,
                        viewModel::setSelectedDisplayMode,
                        selectedSortOrder,
                        viewModel::setSelectedSortOrder
                    )
                }

                items(items = myItems, itemContent = { item ->
                    Text(text = item, style = TextStyle(fontSize = 80.sp))
                })
            }
        }
    }
}

