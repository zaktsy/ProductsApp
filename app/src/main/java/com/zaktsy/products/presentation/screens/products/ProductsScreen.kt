package com.zaktsy.products.presentation.screens.products

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.zaktsy.products.R
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.presentation.navigation.NavigationRoutes.Companion.EditProductRoot
import com.zaktsy.products.ui.components.*
import com.zaktsy.products.ui.theme.*

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalPermissionsApi::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navController: NavController,
    scrollState: LazyListState,
    needToUpdateProducts: MutableState<Boolean>
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

    if (needToUpdateProducts.value) {
        viewModel.getProducts()
        needToUpdateProducts.value = false
    }

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    Scaffold(backgroundColor = MaterialTheme.colorScheme.background, floatingActionButton = {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            AnimatedFAB(scrollState, 10.dp, 40.dp, Icons.Default.QrCode) {
                cameraPermissionState.launchPermissionRequest()
                navController.navigate(
                    NavigationRoutes.BarcodeScanner
                )
            }

            AnimatedFAB(
                scrollState, 90.dp
            ) { navController.navigate(NavigationRoutes.AddProduct) }
        }
    }) {
        Column {
            LazyColumn(
                state = scrollState,
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = R.string.products),
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }

                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                }

                if (showProducts.value and products.value.isNotEmpty() and !isLoading.value) {
                    items(items = products.value,
                        key = { product -> product.id },
                        itemContent = { item ->
                            val currentItem by rememberUpdatedState(item)
                            val dismissState = rememberDismissState(confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.removeProductFromProducts(currentItem)
                                    true
                                } else false
                            })
                            SwipeToDismiss(state = dismissState,
                                modifier = Modifier.animateItemPlacement(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessMediumLow
                                    )
                                ),
                                dismissThresholds = { direction ->
                                    FractionalThreshold(
                                        if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
                                    )
                                },
                                background = {
                                    val color = when (dismissState.dismissDirection) {
                                        DismissDirection.StartToEnd -> Color.Transparent
                                        DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
                                        null -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 20.dp, vertical = 10.dp)
                                            .clip(RoundedCornerShape(25.dp))
                                            .background(color)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onError,
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 20.dp)
                                        )
                                    }

                                },
                                dismissContent = {
                                    ProductElement(
                                        item.percentageDueExpiration,
                                        text = item.name,
                                        daysToExpiration = item.daysDueExpiration.toString()
                                    ) {
                                        navController.navigate(buildTwoRoute(item.id.toString()))
                                    }
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        })
                }

                if (showGroupedProducts.value and groupedProducts.value.isNotEmpty() and !isLoading.value) {
                    items(items = groupedProducts.value) { item ->
                        ExpandableList(
                            title = item.groupingModel.name,
                            item.products.count().toString(),
                            products = item.products,
                            navController = navController,
                            onSwipeToDismiss = viewModel::removeProductFromGroupedProducts
                        )

                    }
                }

                item {
                    SmartSpacer(scrollState)
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

private fun buildTwoRoute(argument: String) = "$EditProductRoot/$argument"