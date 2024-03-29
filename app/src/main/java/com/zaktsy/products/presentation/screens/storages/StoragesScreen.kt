package com.zaktsy.products.presentation.screens.storages

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
import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.ui.components.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StoragesScreen(
    navController: NavController,
    scrollState: LazyListState,
    needToUpdateProducts: MutableState<Boolean>,
    needToUpdateTemplates: MutableState<Boolean>
) {
    var recentlyEditedStorage = Storage("")

    val viewModel = hiltViewModel<StoragesViewModel>()
    val searchEnteredName = viewModel.searchedValue.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val storages = viewModel.storages.collectAsState()
    val allStorages = viewModel.allStorages.collectAsState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val addDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val addedStorageName: MutableState<String> = remember { mutableStateOf("") }

    val editDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }
    val editedStorageName: MutableState<String> = remember { mutableStateOf("") }

    Scaffold(backgroundColor = MaterialTheme.colorScheme.background,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AnimatedFAB(scrollState, 10.dp) { addDialogOpenedState.value = true }
        }) {

        Column {

            StorageDialog(
                stringResource(id = R.string.add_storage_name),
                addDialogOpenedState,
                addedStorageName,
                allStorages.value,
                coroutineScope,
                scaffoldState
            ) {
                viewModel.addStorage(Storage(addedStorageName.value))
                needToUpdateProducts.value = true
            }
            StorageDialog(
                stringResource(id = R.string.edit_storage_name),
                editDialogOpenedState,
                editedStorageName,
                allStorages.value,
                coroutineScope,
                scaffoldState
            ) {
                viewModel.editStorage(recentlyEditedStorage, editedStorageName.value)
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
                            stringResource(id = R.string.storages),
                            searchEnteredName,
                            viewModel::onSearchValueChanged,
                            showBackButton = true,
                            onBackAction = navController::popBackStack
                        )
                    }

                    if (!isLoading.value and storages.value.isNotEmpty()) {
                        items(items = storages.value,
                            key = { storage -> storage.id },
                            itemContent = { item ->

                                val currentItem by rememberUpdatedState(item)
                                val dismissState = rememberDismissState(confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        viewModel.deleteStorage(currentItem)
                                        needToUpdateProducts.value = true
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
                                                .padding(horizontal = 20.dp, vertical = 5.dp)
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
                                            editedStorageName.value = item.name
                                            editDialogOpenedState.value = true
                                            recentlyEditedStorage = item
                                        }
                                    },
                                    directions = setOf(DismissDirection.EndToStart)
                                )
                            })
                    }

                    item {
                        SmartSpacer(scrollState)
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

                if (storages.value.isEmpty() and !isLoading.value) {
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
    allStorages: List<Storage>,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    onSubmit: () -> Unit
) {
    val okString = stringResource(id = R.string.ok)
    val storageExistsMessage = stringResource(id = R.string.storage_exists)

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
                val isNameNew = !allStorages.any { it.name == typedName.value }
                if (isNameNew) {
                    onSubmit()
                } else {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = storageExistsMessage, actionLabel = okString
                        )
                    }
                }
                typedName.value = ""
            }
        }
    }
}