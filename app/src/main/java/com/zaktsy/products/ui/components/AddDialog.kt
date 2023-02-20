package com.zaktsy.products.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaktsy.products.R

@Composable
fun AddDialog(
    message: String,
    openDialogState: MutableState<Boolean>,
    editMessage: MutableState<String>,
    onOkAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.background, )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = message, fontSize = 20.sp, color = MaterialTheme.colorScheme.tertiary
            )

            TextField(
                value = editMessage.value,
                onValueChange = { editMessage.value = it },
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.tertiary,
                    disabledTextColor = MaterialTheme.colorScheme.secondary,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            Button(
                onClick = {
                    openDialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(stringResource(id = R.string.cancel))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                enabled = editMessage.value.isNotEmpty(), onClick = {
                    onOkAction()
                    openDialogState.value = false
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(stringResource(id = R.string.add))
            }
        }
    }
}

@Composable
@Preview
fun PreviewAddDialog() {
    val openDialog: MutableState<Boolean> = remember { mutableStateOf(true) }
    val editMessage: MutableState<String> = remember { mutableStateOf("") }

    AddDialog("message", openDialog, editMessage) {}
}