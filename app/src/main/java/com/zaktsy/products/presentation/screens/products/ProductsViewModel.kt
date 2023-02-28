package com.zaktsy.products.presentation.screens.products

import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.usecases.products.GetProductsUseCase
import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import com.zaktsy.products.utils.ProductDisplayMode
import com.zaktsy.products.utils.ProductsSortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModelWithSearch() {

    private val _showProducts = MutableStateFlow(true)
    val showProducts = _showProducts.asStateFlow()

    private val _selectedDisplayMode = MutableStateFlow(2)
    val selectedDisplayMode = _selectedDisplayMode.asStateFlow()

    fun setSelectedDisplayMode(value: Int) {
        _selectedDisplayMode.value = value
        getProducts()
    }

    private val _selectedSortOrder = MutableStateFlow(0)
    val selectedSortOrder = _selectedSortOrder.asStateFlow()

    fun setSelectedSortOrder(value: Int) {
        _selectedSortOrder.value = value
        getProducts()
    }

    private val _products = MutableStateFlow(emptyList<Product>())
    val products = _products.asStateFlow()

    init {
        getProducts()
    }

    @Synchronized
    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val sortOrder = when (_selectedSortOrder.value) {
                0 -> ProductsSortOrder.ALPHABETICALLY
                1 -> ProductsSortOrder.EXPIRATION
                else -> {
                    ProductsSortOrder.EXPIRATION
                }
            }

            when (_selectedDisplayMode.value) {
                0 -> {
                    ProductDisplayMode.STORAGE
                }
                1 -> {
                    ProductDisplayMode.CATEGORY
                }
                else -> {
                    ProductDisplayMode.ALL
                    val items = getProductsUseCase.invoke(sortOrder, _searchedValue.value)
                    _products.value = items
                }
            }
        }
    }

    override fun onSearchValueChanged(text: String) {
        _searchedValue.value = text
        getProducts()
    }
}