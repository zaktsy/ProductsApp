package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleWithBackButton(
    title: String, onBackAction: () -> Unit
) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
        shape = RoundedCornerShape(25.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {onBackAction()}
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Drop Down Arrow",
                )
            }
            Box(
                modifier = Modifier
                    .weight(6f)
                    .fillMaxWidth(),
                ) {
                Text(
                    text = title,
                    modifier = Modifier.align(Center),
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
@Preview
fun PreviewTitle() {
    TitleWithBackButton("fdfds") {}
}