package com.zaktsy.products.presentation.screens.addproduct

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
import com.zaktsy.products.ui.components.ExpandableSelector
import com.zaktsy.products.ui.components.ReadonlyTextField
import com.zaktsy.products.ui.components.TextInput
import com.zaktsy.products.ui.components.TitleWithBackButton
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddProductScreen(
    navController: NavController, needToUpdate: MutableState<Boolean>
) {
    val viewModel = hiltViewModel<AddProductViewModel>()
    val productName = viewModel.productName.collectAsState()
    val manufactureDateString = viewModel.manufactureDateString.collectAsState()
    val manufactureDateDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val expirationDateString = viewModel.expirationDateString.collectAsState()
    val expirationDateDialogOpenedState: MutableState<Boolean> = remember { mutableStateOf(false) }

    val categoriesSelectorExpanded = remember { mutableStateOf(false) }
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryIndex = remember { mutableStateOf(-1) }

    val storagesSelectorExpanded = remember { mutableStateOf(false) }
    val storages = viewModel.storages.collectAsState()
    val selectedStorageIndex = remember { mutableStateOf(-1) }

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
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
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = stringResource(id = R.string.expiration_range),
                style = TextStyle(
                    fontSize = 25.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Row() {
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
                selectedItemIndex = selectedCategoryIndex
            )

            ExpandableSelector(
                expanded = storagesSelectorExpanded,
                label = stringResource(id = R.string.storage),
                items = storages.value,
                selectedItemIndex = selectedStorageIndex
            )

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = stringResource(id = R.string.notifications),
                style = TextStyle(
                    fontSize = 25.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    enabled = productName.value.isNotEmpty(), onClick = {
                        viewModel.addProduct(
                            selectedCategoryIndex.value,
                            selectedStorageIndex.value
                        )
                        needToUpdate.value = true
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