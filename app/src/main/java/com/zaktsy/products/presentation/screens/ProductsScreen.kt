package com.zaktsy.products.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import com.zaktsy.products.ui.components.AnimatedFAB

@Composable
fun ProductsScreen(
    navController: NavController,
    scrollState: LazyListState
) {
    Scaffold(
        floatingActionButton = {
            AnimatedFAB(navController, scrollState)
    },
    ) {
        val myItems = mutableListOf<String>()
        repeat(40) {
            myItems.add("Item $it")
        }

        LazyColumn(
            state = scrollState,
        ) {
            items(items = myItems, itemContent = { item ->
                Text(text = item, style = TextStyle(fontSize = 80.sp))
            })
        }

    }
}

@Composable
@Preview(showBackground = true)
fun PreviewProductsScreen(){
    val navController = rememberNavController()

}