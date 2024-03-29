package com.zaktsy.products.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleListElement(
    title: String, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
        backgroundColor = (MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    text = title,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.weight(6f)
                )
            }
        }
    }
}