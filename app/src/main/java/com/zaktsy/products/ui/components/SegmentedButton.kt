package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun SegmentedButton(
    items: List<String>,
    selectedItemIndex: State<Int>,
    itemSelection: (selectedItemIndex: Int) -> Unit
) {
    val firstItemIndex = 0
    val lastItemIndex = items.lastIndex

    Row(
        modifier = Modifier
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = {
                    itemSelection(index)
                },
                modifier = when (index) {
                    firstItemIndex -> {
                        Modifier
                            .fillMaxWidth()
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedItemIndex.value == index) 1f else 0f)
                            .weight(1f)
                    }
                    else -> {
                        Modifier
                            .fillMaxWidth()
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedItemIndex.value == index) 1f else 0f)
                            .weight(1f)
                    }
                },
                shape = when (index) {
                    firstItemIndex -> RoundedCornerShape(
                        topStartPercent = 100,
                        topEndPercent = 0,
                        bottomStartPercent = 100,
                        bottomEndPercent = 0
                    )
                    lastItemIndex -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 100,
                        bottomStartPercent = 0,
                        bottomEndPercent = 100
                    )
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                colors = if (selectedItemIndex.value == index) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                },
            ) {
                Text(
                    text = item,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedItemIndex.value == index) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }
                )
            }
        }
    }
}