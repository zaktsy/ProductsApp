package com.zaktsy.products.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.ExpandableSearch

@Composable
fun ProductsScreen(
    navController: NavController, scrollState: LazyListState
) {
    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 90.dp) { navController.navigate(NavigationRoutes.AddProduct) }
    }) {
        val myItems = mutableListOf<String>()
        repeat(40) {
            myItems.add("Item $it")
        }
        Column() {
            AnimatedVisibility(visible = scrollState.firstVisibleItemIndex == 0) {
                Column(
                    modifier = Modifier.animateContentSize(
                        animationSpec = tween(
                            durationMillis = 400, easing = LinearOutSlowInEasing
                        )
                    )
                ) {
                    ExpandableSearch(false, scrollState)
                }
            }
            LazyColumn(
                state = scrollState,
            ) {

                items(items = myItems, itemContent = { item ->
                    Text(text = item, style = TextStyle(fontSize = 80.sp))
                })
            }
        }
    }
}
