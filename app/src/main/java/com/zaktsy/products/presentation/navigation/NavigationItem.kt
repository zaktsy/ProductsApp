package com.zaktsy.products.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.zaktsy.products.R

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: Int) {
    object Products : NavigationItem(NavigationRoutes.Products, Icons.Default.Kitchen, R.string.products)
    object AddProduct : NavigationItem(NavigationRoutes.AddProduct, Icons.Default.Add, R.string.addProducts)
    object Settings: NavigationItem(NavigationRoutes.Settings, Icons.Default.Settings, R.string.settings)
    object Categories: NavigationItem(NavigationRoutes.Categories, Icons.Default.Add, R.string.categories)
    object Storages: NavigationItem(NavigationRoutes.Storages, Icons.Default.Add, R.string.storages)
    object Instruction: NavigationItem(NavigationRoutes.Instruction, Icons.Default.Add, R.string.instruction)
    object EditProduct : NavigationItem(NavigationRoutes.EditProduct, Icons.Default.Add, R.string.edit_product)
    object ProductTemplates : NavigationItem(NavigationRoutes.ProductTemplates, Icons.Default.Description, R.string.product_templates)
    object EditTemplate : NavigationItem(NavigationRoutes.EditTemplate, Icons.Default.Add, R.string.edit_template)
    object BarcodeScanner : NavigationItem(NavigationRoutes.BarcodeScanner, Icons.Default.Add, R.string.scan_barcode)
    object AddProductWithBarCode: NavigationItem(NavigationRoutes.AddProductWithBarCode, Icons.Default.Add, R.string.add_product)
}
