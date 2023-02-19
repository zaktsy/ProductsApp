package com.zaktsy.products.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.Search

@Composable
fun CategoriesScreen(
    navController: NavController, scrollState: LazyListState
){
    Scaffold(
        floatingActionButton = {
            AnimatedFAB(navController, scrollState, NavigationRoutes.AddProduct, 10.dp)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val myItems = mutableListOf<String>()
            repeat(40) {
                myItems.add("Item $it")
            }

            AnimatedVisibility(visible = scrollState.firstVisibleItemIndex == 0) {
                Column(
                    modifier = Modifier.animateContentSize(
                        animationSpec = tween(
                            durationMillis = 400, easing = LinearOutSlowInEasing
                        )
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ){
                            Text(
                                text = stringResource(id = R.string.categories),
                                fontSize = 40.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(top = 20.dp)
                            )
                        }
                        Search()
                    }
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