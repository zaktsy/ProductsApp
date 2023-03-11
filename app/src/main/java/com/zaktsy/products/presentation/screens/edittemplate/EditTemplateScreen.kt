package com.zaktsy.products.presentation.screens.edittemplate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaktsy.products.R
import com.zaktsy.products.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTemplateScreen(
    navController: NavController, needToUpdateTemplates: MutableState<Boolean>
) {
    val viewModel = hiltViewModel<EditTemplateViewModel>()
    val productTemplateName = viewModel.productTemplateName.collectAsState()
    val barCode = viewModel.barCode.collectAsState()
    val expirationDuration = viewModel.expirationDuration.collectAsState()

    val categoriesSelectorExpanded = remember { mutableStateOf(false) }
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryName = viewModel.selectedCategoryName.collectAsState()
    val selectedCategoryIndex = remember { mutableStateOf(-1) }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TitleWithBackButton(
                title = stringResource(id = R.string.edit_template),
                onBackAction = navController::popBackStack
            )

            TextInput(
                text = productTemplateName,
                label = stringResource(id = R.string.name),
                onValueChanged = viewModel::setProductTemplateName
            )

            TextInputForNumbers(
                text = barCode,
                label = stringResource(id = R.string.bar_code),
                onValueChanged = viewModel::setBarCode
            )

            TextInputForNumbers(
                text = expirationDuration,
                label = stringResource(id = R.string.expiration_duration),
                onValueChanged = viewModel::setExpirationDuration
            )

            ExpandableSelector(
                expanded = categoriesSelectorExpanded,
                label = stringResource(id = R.string.category),
                items = categories.value,
                selectedItemName = selectedCategoryName,
                selectedItemIndex = selectedCategoryIndex,
                onSelectedChanged = viewModel::setSelectedCategoryName
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    enabled = productTemplateName.value.isNotEmpty(), onClick = {
                        viewModel.saveTemplate(
                            selectedCategoryIndex.value
                        )
                        needToUpdateTemplates.value = true
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
        }
    }
}

@Composable
@Preview
fun PreviewEditTemplateScreen() {
    val navController = rememberNavController()
    val needToUpdateTemplates = remember { mutableStateOf(false) }
    EditTemplateScreen(navController, needToUpdateTemplates)
}