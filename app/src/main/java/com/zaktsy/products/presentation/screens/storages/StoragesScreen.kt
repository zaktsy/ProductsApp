package com.zaktsy.products.presentation.screens.storages

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.ui.components.AnimatedFAB
import com.zaktsy.products.ui.components.HeaderWithSearch
import com.zaktsy.products.ui.components.SimpleListElement
import com.zaktsy.products.ui.components.TextFieldDialog

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StoragesScreen(
    navController: NavController, scrollState: LazyListState, needToUpdate: MutableState<Boolean>
) {
    var recentlyEditedStorage = Storage("")

    val viewModel = hiltViewModel<StoragesViewModel>()
    val searchEnteredName = viewModel.searchedValue.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val storages = viewModel.storages.collectAsState()

    val addDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val addedStorageName: MutableState<String> = remember { mutableStateOf("") }

    val editDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val editedStorageName: MutableState<String> = remember { mutableStateOf("") }

    Scaffold(floatingActionButton = {
        AnimatedFAB(scrollState, 10.dp) { addDialogOpenedState.value = true }
    }) {

        Column {

            StorageDialog(
                stringResource(id = R.string.add_category_name),
                addDialogOpenedState,
                addedStorageName
            ) {
                viewModel.addStorage(Storage(addedStorageName.value))
                needToUpdate.value = true
            }
            StorageDialog(
                stringResource(id = R.string.edit_category_name),
                editDialogOpenedState,
                editedStorageName
            ) {
                viewModel.editStorage(recentlyEditedStorage, editedStorageName.value)
                needToUpdate.value = true
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    state = scrollState,
                ) {
                    item {
                        HeaderWithSearch(
                            stringResource(id = R.string.storages), searchEnteredName, viewModel::onSearchValueChanged
                        )
                    }

                    if (!isLoading.value and storages.value.isNotEmpty()) {
                        items(
                            items = storages.value,
                            key = { storage -> storage.id },
                            itemContent = { item ->
                                val buttonIcons = listOf(Icons.Default.Edit, Icons.Default.Delete)

                                val buttonActions = listOf({
                                    editDialogOpenedState.value = true
                                    recentlyEditedStorage = item
                                }, {
                                    viewModel.deleteStorage(item)
                                    needToUpdate.value = true
                                })
                                Row(
                                    modifier = Modifier.animateItemPlacement(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioNoBouncy,
                                            stiffness = Spring.StiffnessMediumLow
                                        )
                                    ),
                                ) {
                                    SimpleListElement(
                                        title = item.name,
                                        buttonActions = buttonActions,
                                        buttonIcons = buttonIcons
                                    )
                                }
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

                if (storages.value.isEmpty() and !isLoading.value){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.primary,
                            text = stringResource(id = R.string.no_storages)
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
fun StorageDialog(
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