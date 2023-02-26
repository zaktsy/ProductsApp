package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    text: State<String>, label: String, onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        value = text.value,
        label = {
            Text(
                text = label, style = TextStyle(fontSize = 15.sp)
            )
        },
        onValueChange = { onValueChanged(it) },
        textStyle = TextStyle(fontSize = 20.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}