package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaktsy.products.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaktsy.products.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductElement(
    percentageDueExpiration: Float,
    text: String,
    daysToExpiration: String,
    onItemClick: () -> Unit,
) {
    Card(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
        backgroundColor = when (percentageDueExpiration) {
            in 0.0..0.25 -> {
                androidx.compose.material.MaterialTheme.colors.RedContainer
            }
            in 0.25..0.5 -> {
                androidx.compose.material.MaterialTheme.colors.YellowContainer
            }
            in 0.5..1.0 -> {
                androidx.compose.material.MaterialTheme.colors.GreenContainer
            }
            else -> {
                MaterialTheme.colorScheme.surfaceVariant
            }
        },

        shape = RoundedCornerShape(25.dp),
        onClick = {
            onItemClick()
        }) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val contentColor = when (percentageDueExpiration) {
                    in 0.0..0.2 -> {
                        androidx.compose.material.MaterialTheme.colors.onRedContainer
                    }
                    in 0.2..0.5 -> {
                        androidx.compose.material.MaterialTheme.colors.onYellowContainer
                    }
                    in 0.5..1.0 -> {
                        androidx.compose.material.MaterialTheme.colors.onGreenContainer
                    }
                    else -> {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                }
                Text(
                    text = text,
                    fontSize = 25.sp,
                    color = contentColor,
                    modifier = Modifier.weight(5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(id = R.string.expiration_percent),
                    fontSize = 15.sp,
                    color = contentColor,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = daysToExpiration,
                    fontSize = 20.sp,
                    color = contentColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewProductElement() {
    ProductElement(
        0.25f, text = "vhdvjd", daysToExpiration = "3"
    ) {}
}