package com.zaktsy.products.presentation.screens.categories

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.ui.components.AddDialog
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.HeaderWithSearch
import com.zaktsy.products.ui.components.SimpleListElement

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navController: NavController, scrollState: LazyListState
) {
    val viewModel = hiltViewModel<CategoriesViewModel>()
    val searchEnteredName = viewModel.searchedValue.collectAsState()

    val dialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val addedCategoryName: MutableState<String> = remember { mutableStateOf("") }
    val displayProgressIndicator: MutableState<Boolean> = remember { mutableStateOf(true) }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 10.dp) { dialogOpenedState.value = true }
    }) {

        Column {

            AddCategoryDialog(dialogOpenedState, addedCategoryName, viewModel)

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
                            viewModel
                        )
                    }

                    when (uiState.categoriesState) {
                        CategoriesUiState.Error -> {

                        }
                        CategoriesUiState.Loading -> {
                            displayProgressIndicator.value = true
                        }
                        is CategoriesUiState.Success -> {
                            displayProgressIndicator.value = false
                            items(
                                items = (uiState.categoriesState as CategoriesUiState.Success).items,
                                key = { it.id },
                                itemContent = { item ->
                                    val buttonIcons =
                                        listOf(Icons.Default.Edit, Icons.Default.Delete)
                                    val buttonActions = listOf({},{})

                                    SimpleListElement(
                                        title = item.name,
                                        buttonActions = buttonActions,
                                        buttonIcons = buttonIcons
                                    )
                                },
                            )
                        }
                    }
                }
                if (uiState.categoriesState == CategoriesUiState.Loading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun AddCategoryDialog(
    dialogOpenedState: MutableState<Boolean>,
    typedName: MutableState<String>,
    viewModel: CategoriesViewModel
) {
    if (dialogOpenedState.value) {
        Dialog(
            onDismissRequest = {
                dialogOpenedState.value = false
                typedName.value = ""
            },
        ) {
            AddDialog(
                stringResource(id = R.string.add_category_name), dialogOpenedState, typedName
            ) {
                viewModel.addCategory(Category(typedName.value))
                typedName.value = ""
            }
        }
    }
}