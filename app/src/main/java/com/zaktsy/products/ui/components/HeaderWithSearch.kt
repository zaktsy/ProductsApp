package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaktsy.products.presentation.screens.ViewModelWithSearch

@Composable
fun HeaderWithSearch(title: String, searchEnteredName: State<String>, viewModel: ViewModelWithSearch) {
    Row(Modifier.padding(bottom = 20.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = title,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            Search(searchEnteredName, viewModel)
        }
    }
}