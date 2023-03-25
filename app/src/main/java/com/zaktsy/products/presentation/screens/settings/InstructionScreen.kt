package com.zaktsy.products.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zaktsy.products.R
import com.zaktsy.products.ui.components.Header
import com.zaktsy.products.ui.theme.GreenContainer
import com.zaktsy.products.ui.theme.RedContainer
import com.zaktsy.products.ui.theme.YellowContainer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InstructionScreen(
    navController: NavController,
) {
    Scaffold(backgroundColor = MaterialTheme.colorScheme.background) {
        Column {
            Header(
                title = stringResource(id = R.string.instruction),
                showBackButton = true,
                onBackAction = navController::popBackStack
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                text = stringResource(id = R.string.product_color_meanings),
                style = TextStyle(
                    fontSize = 22.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                text = stringResource(id = R.string.product_color_meanings_info),
                style = TextStyle(
                    fontSize = 17.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                text = stringResource(id = R.string.meanings_of_colors),
                style = TextStyle(
                    fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(androidx.compose.material.MaterialTheme.colors.GreenContainer)
                        .padding(end = 20.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                    text = stringResource(id = R.string.product_color_green_info),
                    style = TextStyle(
                        fontSize = 17.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(androidx.compose.material.MaterialTheme.colors.YellowContainer)
                        .padding(end = 20.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                    text = stringResource(id = R.string.product_color_yellow_info),
                    style = TextStyle(
                        fontSize = 17.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(androidx.compose.material.MaterialTheme.colors.RedContainer)
                        .padding(end = 20.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                    text = stringResource(id = R.string.product_color_red_info),
                    style = TextStyle(
                        fontSize = 17.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(end = 20.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                    text = stringResource(id = R.string.product_color_expired_info),
                    style = TextStyle(
                        fontSize = 17.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}