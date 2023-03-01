package com.zaktsy.products.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaktsy.products.domain.models.Product

@Composable
fun ExpandableList(
    title: String, products: List<Product>
) {
    var expandedState by remember { mutableStateOf(false) }

    Surface(
        color = Color.Transparent
    ) {
        Card(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp),
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(25.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            expandedState = !expandedState
                        }),
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                            .height(55.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = title,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.weight(6f)
                        )
                    }
                }

                AnimatedVisibility(visible = expandedState) {
                    Column(
                        modifier = Modifier.animateContentSize(
                            animationSpec = tween(
                                durationMillis = 400, easing = LinearOutSlowInEasing
                            )
                        )
                    ) {
                        products.forEach { product ->
                            ProductElement(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                text = product.name,
                                daysToExpiration = "3"
                            )
                        }
                    }
                }
            }
        }
    }
}
