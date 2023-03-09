package com.zaktsy.products.presentation.screens.categories

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.HeaderWithSearch
import com.zaktsy.products.ui.components.SimpleListElement
import com.zaktsy.products.ui.components.TextFieldDialog

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navController: NavController,
    scrollState: LazyListState,
    needToUpdateProducts: MutableState<Boolean>,
    needToUpdateTemplates: MutableState<Boolean>
) {
    var recentlyEditedCategory = Category("")

    val viewModel = hiltViewModel<CategoriesViewModel>()
    val searchEnteredName = viewModel.searchedValue.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val categories = viewModel.categories.collectAsState()

    val addDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val addedCategoryName: MutableState<String> = remember { mutableStateOf("") }

    val editDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val editedCategoryName: MutableState<String> = remember { mutableStateOf("") }

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 10.dp) { addDialogOpenedState.value = true }
    }) {

        Column {

            CategoryDialog(
                stringResource(id = R.string.add_category_name),
                addDialogOpenedState,
                addedCategoryName
            ) {
                viewModel.addCategory(Category(addedCategoryName.value))
                needToUpdateProducts.value = true
                needToUpdateTemplates.value = true
            }
            CategoryDialog(
                stringResource(id = R.string.edit_category_name),
                editDialogOpenedState,
                editedCategoryName
            ) {
                viewModel.editCategory(recentlyEditedCategory, editedCategoryName.value)
                needToUpdateProducts.value = true
                needToUpdateTemplates.value = true
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    state = scrollState,
                ) {
                    item {
                        HeaderWithSearch(
                            stringResource(id = R.string.categories),
                            searchEnteredName,
                            viewModel::onSearchValueChanged
                        )
                    }

                    if (!isLoading.value and categories.value.isNotEmpty()) {
                        items(items = categories.value,
                            key = { category -> category.id },
                            itemContent = { item ->

                                val currentItem by rememberUpdatedState(item)
                                val dismissState = rememberDismissState(confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        viewModel.deleteCategory(currentItem)
                                        needToUpdateProducts.value = true
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
                                            editDialogOpenedState.value = true
                                            recentlyEditedCategory = item
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
                        horizontalAlignment = CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (categories.value.isEmpty() and !isLoading.value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.primary,
                            text = stringResource(id = R.string.no_categories)
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.primary,
                            text = stringResource(id = R.string.add_first_one)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryDialog(
    title: String,
    dialogOpenedState: MutableState<Boolean>,
    typedName: MutableState<String>,
    onSubmit: () -> Unit
) {
    if (dialogOpenedState.value) {
        Dialog(
            onDismissRequest = {
                dialogOpenedState.value = false
                typedName.value = ""
            },
        ) {
            TextFieldDialog(
                title, dialogOpenedState, typedName
            ) {
                onSubmit()
                typedName.value = ""
            }
        }
    }
}