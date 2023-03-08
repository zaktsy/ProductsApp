package com.zaktsy.products.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleListElement(
    title: String, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        backgroundColor = (MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(25.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = title,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.weight(6f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSimpleListElement() {
    val title = "Category"
    val buttonActions = ArrayList<() -> Unit>()
    val buttonIcons = ArrayList<ImageVector>()

    buttonActions.add { }
    buttonIcons.add(Icons.Outlined.Edit)
    buttonActions.add { }
    buttonIcons.add(Icons.Default.Delete)

    SimpleListElement(title) {}
}