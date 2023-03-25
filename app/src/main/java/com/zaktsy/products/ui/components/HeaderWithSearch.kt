package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderWithSearch(
    title: String,
    searchEnteredName: State<String>,
    onSearchValueChanged: (String) -> Unit,
    showBackButton: Boolean = false,
    onBackAction: () -> Unit = {}
) {
    Row(Modifier.padding(bottom = 5.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Header(showBackButton, onBackAction, title)
            Search(searchEnteredName, onSearchValueChanged)
        }
    }
}

@Composable
fun Header(
    showBackButton: Boolean = false, onBackAction: () -> Unit = {}, title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (showBackButton) {
            IconButton(modifier = Modifier.weight(1f), onClick = { onBackAction() }) {
                Icon(
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Drop Down Arrow",
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth(),
        ) {
            Text(
                text = title,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (showBackButton) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
        }
    }
}