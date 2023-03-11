package com.zaktsy.products.presentation.screens.editproduct

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.zaktsy.products.R
import com.zaktsy.products.ui.components.*
import com.zaktsy.products.utils.AlarmType
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditProductScreen(
    navController: NavController, needToUpdateProducts: MutableState<Boolean>
) {
    val viewModel = hiltViewModel<EditProductViewModel>()
    val productName = viewModel.productName.collectAsState()
    val manufactureDateString = viewModel.manufactureDateString.collectAsState()
    val manufactureDateDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val expirationDateString = viewModel.expirationDateString.collectAsState()
    val expirationDateDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val categoriesSelectorExpanded = remember { mutableStateOf(false) }
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryName = viewModel.selectedCategoryName.collectAsState()
    val selectedCategoryIndex = remember { mutableStateOf(-1) }

    val storagesSelectorExpanded = remember { mutableStateOf(false) }
    val storages = viewModel.storages.collectAsState()
    val selectedStorageName = viewModel.selectedStorageName.collectAsState()
    val selectedStorageIndex = remember { mutableStateOf(-1) }

    val notificationStatesFromViewModel = viewModel.notificationStates.collectAsState()
    val notificationStates = HashMap<Int, Pair<AlarmType, MutableState<Boolean>>>()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TitleWithBackButton(
                title = stringResource(id = R.string.edit_product),
                onBackAction = navController::popBackStack
            )

            TextInput(
                text = productName,
                label = stringResource(id = R.string.name),
                onValueChanged = viewModel::setProductName
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = stringResource(id = R.string.expiration_range),
                style = TextStyle(
                    fontSize = 25.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Row {
                ReadonlyTextField(
                    value = manufactureDateString,
                    onClick = { manufactureDateDialogOpenedState.value = true },
                    label = stringResource(id = R.string.manufacture_date)
                )

                ReadonlyTextField(
                    value = expirationDateString,
                    onClick = { expirationDateDialogOpenedState.value = true },
                    label = stringResource(id = R.string.expiration_date)
                )
            }


            ExpandableSelector(
                expanded = categoriesSelectorExpanded,
                label = stringResource(id = R.string.category),
                items = categories.value,
                selectedItemName = selectedCategoryName,
                selectedItemIndex = selectedCategoryIndex,
                onSelectedChanged = viewModel::setSelectedCategoryName
            )

            ExpandableSelector(
                expanded = storagesSelectorExpanded,
                label = stringResource(id = R.string.storage),
                items = storages.value,
                selectedItemIndex = selectedStorageIndex,
                selectedItemName = selectedStorageName,
                onSelectedChanged = viewModel::setSelectedStorageName
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = stringResource(id = R.string.notifications),
                style = TextStyle(
                    fontSize = 25.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Column {
                if (notificationStatesFromViewModel.value.containsKey(AlarmType.ONE_DAY_BEFORE)) {
                    notificationStatesFromViewModel.value[AlarmType.ONE_DAY_BEFORE]?.let {
                        notificationStates[R.string.one_day_before] =
                            Pair(AlarmType.ONE_DAY_BEFORE, remember { mutableStateOf(it) })
                    }
                } else {
                    notificationStates[R.string.one_day_before] =
                        Pair(AlarmType.ONE_DAY_BEFORE, remember { mutableStateOf(false) })
                }

                if (notificationStatesFromViewModel.value.containsKey(AlarmType.TWO_DAYS_BEFORE)) {
                    notificationStatesFromViewModel.value[AlarmType.TWO_DAYS_BEFORE]?.let {
                        notificationStates[R.string.two_day_before] =
                            Pair(AlarmType.TWO_DAYS_BEFORE, remember { mutableStateOf(it) })
                    }
                } else {
                    notificationStates[R.string.two_day_before] =
                        Pair(AlarmType.TWO_DAYS_BEFORE, remember { mutableStateOf(false) })
                }

                if (notificationStatesFromViewModel.value.containsKey(AlarmType.ONE_WEEK_BEFORE)) {
                    notificationStatesFromViewModel.value[AlarmType.ONE_WEEK_BEFORE]?.let {
                        notificationStates[R.string.one_week_before] =
                            Pair(AlarmType.ONE_WEEK_BEFORE, remember { mutableStateOf(it) })
                    }
                } else {
                    notificationStates[R.string.one_week_before] =
                        Pair(AlarmType.ONE_WEEK_BEFORE, remember { mutableStateOf(false) })
                }

                notificationStates.forEach {
                    CheckableItem(
                        checked = it.value.second, it.key
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    enabled = productName.value.isNotEmpty(), onClick = {
                        viewModel.saveProduct(
                            selectedCategoryIndex.value, selectedStorageIndex.value
                        )
                        viewModel.updateNotifications(notificationStates.values)
                        needToUpdateProducts.value = true
                        navController.popBackStack()
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(stringResource(id = R.string.save))
                }
            }


            if (manufactureDateDialogOpenedState.value) {
                DatePickerDialog(
                    onDismissRequest = { manufactureDateDialogOpenedState.value = false },
                    onDateChange = {
                        viewModel.setManufactureDate(
                            Date.from(
                                it.atStartOfDay().atZone(
                                    ZoneId.systemDefault()
                                ).toInstant()
                            )
                        )
                        manufactureDateDialogOpenedState.value = false
                    },
                    typography = DatePickerDefaults.typography(
                        day = TextStyle(fontWeight = FontWeight.Medium),
                        monthYear = TextStyle(fontWeight = FontWeight.Medium),
                    ),
                    containerColor = MaterialTheme.colorScheme.background,
                )
            }

            if (expirationDateDialogOpenedState.value) {
                DatePickerDialog(
                    onDismissRequest = { expirationDateDialogOpenedState.value = false },
                    onDateChange = {
                        viewModel.setExpirationDate(
                            Date.from(
                                it.atStartOfDay().atZone(
                                    ZoneId.systemDefault()
                                ).toInstant()
                            )
                        )
                        expirationDateDialogOpenedState.value = false
                    },
                    typography = DatePickerDefaults.typography(
                        day = TextStyle(fontWeight = FontWeight.Medium),
                        monthYear = TextStyle(fontWeight = FontWeight.Medium),
                    ),
                    containerColor = MaterialTheme.colorScheme.background,
                )
            }
        }
    }
}