package com.zaktsy.products.presentation.screens.addproduct

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.utils.AlarmType
import com.zaktsy.products.ui.components.*
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddProductScreen(
    navController: NavController,
    needToUpdateProducts: MutableState<Boolean>,
    needToUpdateTemplates: MutableState<Boolean>
) {
    val viewModel = hiltViewModel<AddProductViewModel>()
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

    val saveProductAsTemplate = viewModel.saveProductAsTemplate.collectAsState()

    val notificationStates = HashMap<Int, Pair<AlarmType, MutableState<Boolean>>>()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            TitleWithBackButton(
                title = stringResource(id = R.string.add_product),
                onBackAction = navController::popBackStack
            )

            TextInput(
                text = productName,
                label = stringResource(id = R.string.name),
                onValueChanged = viewModel::setProductName
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                text = stringResource(id = R.string.expiration_range),
                style = TextStyle(
                    fontSize = 22.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
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
                selectedItemName = selectedStorageName,
                selectedItemIndex = selectedStorageIndex,
                onSelectedChanged = viewModel::setSelectedStorageName
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 15.dp),
                text = stringResource(id = R.string.notifications),
                style = TextStyle(
                    fontSize = 22.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )


            Column {
                notificationStates[R.string.one_day_before] =
                    Pair(AlarmType.ONE_DAY_BEFORE, remember { mutableStateOf(false) })
                notificationStates[R.string.two_day_before] =
                    Pair(AlarmType.TWO_DAYS_BEFORE, remember { mutableStateOf(false) })
                notificationStates[R.string.one_week_before] =
                    Pair(AlarmType.ONE_WEEK_BEFORE, remember { mutableStateOf(false) })

                notificationStates.forEach {
                    CheckableItem(
                        checked = it.value.second, it.key
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 20.dp, top = 5.dp, end = 20.dp),
                    text = stringResource(id = R.string.save_as_template),
                    style = TextStyle(
                        fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Switch(checked = saveProductAsTemplate.value,
                    onCheckedChange = { viewModel.setSaveProductAsTemplate(it) })
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    enabled = productName.value.isNotEmpty(), onClick = {
                        viewModel.addProductAndNotifications(
                            selectedCategoryIndex.value,
                            selectedStorageIndex.value,
                            notificationStates.values
                        )
                        viewModel.saveProductAsTemplate(selectedCategoryIndex.value)
                        needToUpdateTemplates.value = true
                        needToUpdateProducts.value = true
                        navController.navigate(NavigationRoutes.Products){
                            popUpTo(0)
                        }
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