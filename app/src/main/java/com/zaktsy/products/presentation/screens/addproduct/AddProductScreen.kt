package com.zaktsy.products.presentation.screens.addproduct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.zaktsy.products.R
import com.zaktsy.products.ui.components.TextInput
import com.zaktsy.products.ui.components.TitleWithBackButton
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddProductScreen(
    navController: NavController
) {
    val name = remember { mutableStateOf("")}
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TitleWithBackButton(
                title = stringResource(id = R.string.add_product),
                onBackAction = navController::popBackStack
            )

            TextInput(text = name, label = stringResource(id = R.string.name))

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                text = stringResource(id = R.string.expiration_range),
                style = TextStyle(
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            TextInput(text = name, label = stringResource(id = R.string.name))

            DatePickerDialog(
                onDismissRequest = {  },
                onDateChange = {},
                typography = DatePickerDefaults.typography(
                    day = TextStyle(fontWeight = FontWeight.Medium),
                    monthYear = TextStyle(fontWeight = FontWeight.Medium),
                ),
                containerColor = MaterialTheme.colorScheme.background,
            )

        }
    }
}

@Composable
@Preview
fun PreviewAddProductScreen() {
    val navController = rememberNavController()
    AddProductScreen(navController)
}