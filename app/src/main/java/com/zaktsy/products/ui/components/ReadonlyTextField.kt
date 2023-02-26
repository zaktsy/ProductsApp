package com.zaktsy.products.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadonlyTextField(
    value: State<String>, onClick: () -> Unit, label: String
) {
    Box(
        modifier = Modifier
            .width(170.dp)
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = { },
            label = {
                Text(
                    text = label, style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center)
                )
            },
            textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}