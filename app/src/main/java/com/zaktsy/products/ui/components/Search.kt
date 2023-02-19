package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Search() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Card(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 15.dp),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(55.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = "Search",
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    singleLine = true,
                    modifier = Modifier.weight(6f),
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledTextColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}