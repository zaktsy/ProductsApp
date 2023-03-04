package com.zaktsy.products.presentation.screens.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.presentation.navigation.NavigationRoutes.Companion.EditProductRoot
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.ExpandableList
import com.zaktsy.products.ui.components.ExpandableSearch
import com.zaktsy.products.ui.components.ProductElement
import com.zaktsy.products.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navController: NavController, scrollState: LazyListState, needToUpdate: MutableState<Boolean>
) {

    val viewModel = hiltViewModel<ProductsViewModel>()
    val isLoading = viewModel.isLoading.collectAsState()
    val selectedDisplayMode = viewModel.selectedDisplayMode.collectAsState()
    val selectedSortOrder = viewModel.selectedSortOrder.collectAsState()
    val searchedValue = viewModel.searchedValue.collectAsState()

    val showGroupedProducts = viewModel.showGroupedProducts.collectAsState()
    val groupedProducts = viewModel.groupedProducts.collectAsState()

    val showProducts = viewModel.showProducts.collectAsState()
    val products = viewModel.products.collectAsState()

    if (needToUpdate.value) {
        viewModel.getProducts()
        needToUpdate.value = false
    }

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

                if (showProducts.value and products.value.isNotEmpty() and !isLoading.value) {
                    items(items = products.value, itemContent = { item ->
                        ProductElement(
                            item.percentageDueExpiration,
                            text = item.name,
                            daysToExpiration = item.daysDueExpiration.toString()
                        ) {
                            navController.navigate(buildTwoRoute(item.id.toString()))
                        }
                    })
                }

                if (showGroupedProducts.value and groupedProducts.value.isNotEmpty() and !isLoading.value) {
                    items(items = groupedProducts.value) { item ->
                        ExpandableList(
                            item.groupingModel.name, item.products, navController
                        )

                    }
                }

            }

            if (isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

fun buildTwoRoute(argument: String) = "$EditProductRoot/$argument"