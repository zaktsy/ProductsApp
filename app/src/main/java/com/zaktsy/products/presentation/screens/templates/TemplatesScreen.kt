package com.zaktsy.products.presentation.screens.templates

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.ui.components.HeaderWithSearch
import com.zaktsy.products.ui.components.Search
import com.zaktsy.products.ui.components.SimpleListElement

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun TemplatesScreen(
    navController: NavController,
    scrollState: LazyListState,
    needToUpdateTemplates: MutableState<Boolean>
) {

    val viewModel = hiltViewModel<TemplatesViewModel>()

    val isLoading = viewModel.isLoading.collectAsState()
    val searchedValue = viewModel.searchedValue.collectAsState()

    val productTemplates = viewModel.productTemplates.collectAsState()

    if (needToUpdateTemplates.value) {
        viewModel.getTemplates()
        needToUpdateTemplates.value = false
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        Column {
            LazyColumn(
                state = scrollState,
            ) {
                item {
                    HeaderWithSearch(
                        title = stringResource(id = R.string.templates),
                        searchEnteredName = searchedValue,
                        onSearchValueChanged = viewModel::onSearchValueChanged
                    )
                }

                if (!isLoading.value) {
                    items(items = productTemplates.value,
                        key = { product -> product.id },
                        itemContent = { item ->

                            val currentItem by rememberUpdatedState(item)
                            val dismissState = rememberDismissState(confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.deleteTemplate(currentItem)
                                    needToUpdateTemplates.value = true
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
                                    SimpleListElement(
                                        title = item.name,
                                    ) {
                                        navController.navigate(buildTwoRoute(item.id.toString()))
                                    }
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        })
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

private fun buildTwoRoute(argument: String) = "${NavigationRoutes.EditTemplateRoot}/$argument"
