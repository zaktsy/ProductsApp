package com.zaktsy.products.presentation.screens.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.ExpandableSearch
import com.zaktsy.products.ui.components.ProductElement

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navController: NavController, scrollState: LazyListState
) {

    val viewModel = hiltViewModel<ProductsViewModel>()
    val selectedDisplayMode = viewModel.selectedDisplayMode.collectAsState()
    val selectedSortOrder = viewModel.selectedSortOrder.collectAsState()
    val searchedValue = viewModel.searchedValue.collectAsState()

    val showProducts = viewModel.showProducts.collectAsState()
    val products = viewModel.products.collectAsState()

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
                        viewModel::setSelectedSortOrder,
                        viewModel::onSearchValueChanged,
                        searchedValue
                    )
                }

                if (showProducts.value){
                    items(items = products.value, itemContent = { item ->
                        ProductElement(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            text = item.name,
                            daysToExpiration = "3"
                        )
                    })
                }

            }
        }
    }
}

