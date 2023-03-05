package com.zaktsy.products.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.presentation.navigation.NavigationRoutes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableList(
    title: String,
    products: MutableList<Product>,
    navController: NavController,
    onSwipeToDismiss: (Product) -> Unit
) {
    var expandedState by remember { mutableStateOf(false) }

    Surface(
        color = Color.Transparent
    ) {
        Card(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp),
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(25.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            expandedState = !expandedState
                        }),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                            .height(55.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = title,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.weight(6f)
                        )
                    }
                }

                AnimatedVisibility(visible = expandedState) {
                    Column(
                        modifier = Modifier.animateContentSize(
                            animationSpec = tween(
                                durationMillis = 400, easing = LinearOutSlowInEasing
                            )
                        )
                    ) {
                        products.forEach { item ->
                            val currentItem by rememberUpdatedState(item)
                            val dismissState = rememberDismissState(confirmStateChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    onSwipeToDismiss(currentItem)
                                    products.remove(currentItem)
                                    true
                                } else false
                            })
                            SwipeToDismiss(
                                state = dismissState,
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
                                        percentageDueExpiration = item.percentageDueExpiration,
                                        text = item.name,
                                        daysToExpiration = "3"
                                    ) {
                                        navController.navigate(buildTwoRoute(item.id.toString()))
                                    }
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun buildTwoRoute(argument: String) = "${NavigationRoutes.EditProductRoot}/$argument"
