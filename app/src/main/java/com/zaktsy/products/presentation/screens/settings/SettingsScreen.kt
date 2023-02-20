package com.zaktsy.products.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaktsy.products.R
import com.zaktsy.products.presentation.navigation.NavigationRoutes

@Composable
fun SettingsScreen(
    navController: NavController
) {
    Scaffold() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
            ){
                Text(
                    text = stringResource(id = R.string.settings),
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                )
            }
            Box(modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .clip(RoundedCornerShape(100.dp))
                .clickable { navController.navigate(NavigationRoutes.Categories) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 2.dp),
                        text = stringResource(id = R.string.categories),
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = "Right",
                        Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewSettingsScreen() {
    val navController = rememberNavController()
    SettingsScreen(navController)
}