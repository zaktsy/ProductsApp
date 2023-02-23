package com.zaktsy.products.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaktsy.products.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableSearch(
    expanded: Boolean, scrollState: LazyListState
) {
    var expandedState by remember { mutableStateOf(expanded) }
    val rotationState by animateFloatAsState(if (expandedState) 180f else 0f)
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Surface(
        color = Color.Transparent
    ) {
        Card(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 15.dp),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(25.dp),
            onClick = {
                expandedState = !expandedState
            }) {
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
                    IconButton(modifier = Modifier
                        .rotate(rotationState)
                        .weight(1f), onClick = {
                        expandedState = !expandedState
                    }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Drop Down Arrow",
                        )
                    }
                }
                AnimatedVisibility(visible = expandedState and (scrollState.firstVisibleItemIndex == 0)) {
                    Column(
                        modifier = Modifier.animateContentSize(
                            animationSpec = tween(
                                durationMillis = 400, easing = LinearOutSlowInEasing
                            )
                        )
                    ) {
                        Divider(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.productDisplayMode),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 5.dp)
                        )

                        val productsDisplayModes = listOf(
                            stringResource(id = R.string.storage),
                            stringResource(id = R.string.category),
                            stringResource(id = R.string.all)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(alignment = CenterHorizontally)
                        ) {
                            SegmentedButton(
                                items = productsDisplayModes, defaultSelectedItemIndex = 1
                            ) {}
                        }

                        Text(
                            text = stringResource(id = R.string.sortOrder),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 5.dp)
                        )

                        val sortOrders = listOf(
                            stringResource(id = R.string.alphabetically),
                            stringResource(id = R.string.expiration)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                                .align(alignment = CenterHorizontally)
                        ) {
                            SegmentedButton(items = sortOrders, defaultSelectedItemIndex = 1) {}
                        }
                    }
                }
            }
        }
    }

}