package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckableItem(
    checked: MutableState<Boolean>, stringId: Int
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 7.dp)
            .height(25.dp),
        backgroundColor = if (!checked.value) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(25.dp),
        onClick = {
            checked.value = !checked.value
        }) {

        Row(
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .widthIn(min = 110.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val contentColor = if (!checked.value) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onPrimary

            Text(
                text = stringResource(id = stringId),
                fontSize = 15.sp,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}