package com.zaktsy.products.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductElement(
    backgroundColor: Color,
    contentColor: Color,
    text: String,
    daysToExpiration: String
){
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(55.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    fontSize = 25.sp,
                    color = contentColor,
                    modifier = Modifier.weight(6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "due",
                    fontSize = 25.sp,
                    color = contentColor,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = daysToExpiration,
                    fontSize = 25.sp,
                    color = contentColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewProductElement(){
    ProductElement(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        text = "vhdvjd",
        daysToExpiration = "3"
    )
}