package com.zaktsy.products.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AnimatedFAB(
    navController: NavController,
    scrollState: LazyListState,
    navigationRoute: String
) {
    AnimatedVisibility(
        visible = scrollState.firstVisibleItemIndex == 0,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it }),
        content = {
            FloatingActionButton(
                modifier = Modifier.padding(0.dp,0.dp,0.dp,90.dp),
                onClick = { navController.navigate(navigationRoute) },
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = "Add FAB")
            }
        }
    )
}