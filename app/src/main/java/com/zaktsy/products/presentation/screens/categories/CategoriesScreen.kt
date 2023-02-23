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
import com.zaktsy.products.ui.components.TextFieldDialog
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.HeaderWithSearch
import com.zaktsy.products.ui.components.SimpleListElement

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navController: NavController, scrollState: LazyListState
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

    val displayProgressIndicator: MutableState<Boolean> = remember { mutableStateOf(true) }

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 10.dp) { addDialogOpenedState.value = true }
    }) {

        Column {

            CategoryDialog(stringResource(id = R.string.add_category_name),addDialogOpenedState, addedCategoryName){
                viewModel.addCategory(Category(addedCategoryName.value))
            }
            CategoryDialog(stringResource(id = R.string.edit_category_name),editDialogOpenedState, editedCategoryName){
                viewModel.editCategory(recentlyEditedCategory, editedCategoryName.value)
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    state = scrollState,
                ) {
                    item {
                        HeaderWithSearch(
                            stringResource(id = R.string.categories), searchEnteredName, viewModel
                        )
                    }

                    if (!isLoading.value){
                        displayProgressIndicator.value = false
                        items(
                            items = categories.value,
                            key = { it.id },
                            itemContent = { item ->
                                val buttonIcons =
                                    listOf(Icons.Default.Edit, Icons.Default.Delete)

                                val buttonActions = listOf({
                                    editDialogOpenedState.value = true
                                    recentlyEditedCategory = item
                                }, {
                                    viewModel.deleteCategory(item)
                                })

                                SimpleListElement(
                                    title = item.name,
                                    buttonActions = buttonActions,
                                    buttonIcons = buttonIcons
                                )
                            },
                        )
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