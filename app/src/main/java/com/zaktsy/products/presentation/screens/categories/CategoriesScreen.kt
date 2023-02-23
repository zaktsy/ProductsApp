package com.zaktsy.products.presentation.screens.categories

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.ui.components.AddDialog
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.Search

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navController: NavController, scrollState: LazyListState
) {
    val viewModel = hiltViewModel<CategoriesViewModel>()

    val dialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val typedName: MutableState<String> = remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 10.dp) { dialogOpenedState.value = true }
    }) {

        Column {

            AddCategoryDialog(dialogOpenedState, typedName, viewModel)

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    state = scrollState,
                ) {
                    item { Header() }

                    when (uiState.categoriesState){
                        CategoriesUiState.Error -> {

                        }
                        CategoriesUiState.Loading -> {

                        }
                        is CategoriesUiState.Success -> {
                            items(items = (uiState.categoriesState as CategoriesUiState.Success).items, itemContent = { item ->
                                Text(text = item.name, style = TextStyle(fontSize = 80.sp))
                            })
                        }
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
            }
        }
    }
}

@Composable
fun Header() {
    Row {
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