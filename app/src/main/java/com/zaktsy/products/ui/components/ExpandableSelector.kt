package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaktsy.products.domain.models.ModelWithName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableSelector(
    expanded: MutableState<Boolean>,
    label: String,
    items: List<ModelWithName>,
    selectedItemIndex: MutableState<Int>
) {
    val selectedOptionText = remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value },
        modifier = Modifier
            .width(300.dp)
            .padding(start = 20.dp, top = 10.dp)
    ) {
        OutlinedTextField(singleLine = true,
            readOnly = true,
            modifier = Modifier.menuAnchor(),
            value = selectedOptionText.value,
            onValueChange = { },
            label = {
                Text(
                    text = label, style = TextStyle(fontSize = 15.sp)
                )
            },
            textStyle = TextStyle(fontSize = 20.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded.value
                )
            })
        ExposedDropdownMenu(expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }) {
            items.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(onClick = {
                    selectedOptionText.value = selectionOption.name
                    expanded.value = false
                    selectedItemIndex.value = index
                },
                    text = { Text(text = selectionOption.name) },
                    modifier = Modifier.exposedDropdownSize()
                )
            }
        }
    }
}