package com.zaktsy.products.presentation.screens.categories

import androidx.compose.ui.window.Dialog
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.ui.components.AddDialog
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.Search

@Composable
fun CategoriesScreen(
    navController: NavController, scrollState: LazyListState
) {
    val dialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val typedName: MutableState<String> = remember { mutableStateOf("") }

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 10.dp) { dialogOpenedState.value = true }
    }) {
        if (dialogOpenedState.value) {
            Dialog(
                onDismissRequest = {
                    dialogOpenedState.value = false
                    typedName.value = ""
                },
            ) {
                AddDialog(
                    stringResource(id = R.string.add_category_name), dialogOpenedState, typedName
                ){}
            }
        }

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
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
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