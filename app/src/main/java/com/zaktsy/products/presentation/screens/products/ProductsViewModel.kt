package com.zaktsy.products.presentation.screens.products

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.GroupedProducts
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.usecases.products.GetProductsGropedByCategoryUseCase
import com.zaktsy.products.domain.usecases.products.GetProductsGropedByStorageUseCase
import com.zaktsy.products.domain.usecases.products.GetProductsUseCase
import com.zaktsy.products.domain.usecases.products.RemoveProductUseCase
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
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductsGropedByCategoryUseCase: GetProductsGropedByCategoryUseCase,
    private val getProductsGropedByStorageUseCase: GetProductsGropedByStorageUseCase,
    private val removeProductUseCase: RemoveProductUseCase
) : ViewModelWithSearch() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _showProducts = MutableStateFlow(true)
    val showProducts = _showProducts.asStateFlow()

    private val _showGroupedProducts = MutableStateFlow(false)
    val showGroupedProducts = _showGroupedProducts.asStateFlow()

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

    private val _products = MutableStateFlow(mutableStateListOf<Product>())
    var products = _products.asStateFlow()

    fun removeProductFromProducts(product: Product) {
        _products.value.removeIf { it.id == product.id }
        viewModelScope.launch(Dispatchers.IO) {
            removeProductUseCase.invoke(product)
        }
    }

    private val _gropedProducts = MutableStateFlow(emptyList<GroupedProducts>())
    val groupedProducts = _gropedProducts.asStateFlow()

    fun removeProductFromGroupedProducts(product: Product) {
        val indexOfGroupWithProduct =
            _gropedProducts.value.indexOfFirst { it.products.contains(product) }

        if (indexOfGroupWithProduct != -1)
            _gropedProducts.value[indexOfGroupWithProduct].products.removeIf { it.id == product.id }
        viewModelScope.launch(Dispatchers.IO) {
            removeProductUseCase(product)
            getProducts()
        }
    }

    init {
        getProducts()
    }

    @Synchronized
    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _showProducts.value = false
            _showGroupedProducts.value = false
            val sortOrder = when (_selectedSortOrder.value) {
                0 -> ProductsSortOrder.ALPHABETICALLY
                1 -> ProductsSortOrder.EXPIRATION_DAYS
                2 -> ProductsSortOrder.EXPIRATION_PERCENT
                else -> {
                    ProductsSortOrder.EXPIRATION_PERCENT
                }
            }

            when (_selectedDisplayMode.value) {
                0 -> {
                    ProductDisplayMode.STORAGE
                    val items =
                        getProductsGropedByStorageUseCase.invoke(sortOrder, _searchedValue.value)
                    _isLoading.value = false
                    _showGroupedProducts.value = true
                    _gropedProducts.value = items
                }
                1 -> {
                    ProductDisplayMode.CATEGORY
                    val items =
                        getProductsGropedByCategoryUseCase.invoke(sortOrder, _searchedValue.value)
                    _isLoading.value = false
                    _showGroupedProducts.value = true
                    _gropedProducts.value = items
                }
                else -> {
                    ProductDisplayMode.ALL
                    _showProducts.value = true
                    val items = getProductsUseCase.invoke(sortOrder, _searchedValue.value)
                    _isLoading.value = false
                    _showProducts.value = true
                    _products.value = mutableStateListOf()
                    _products.value.addAll(items)
                }
            }

        }
    }

    override fun onSearchValueChanged(text: String) {
        _searchedValue.value = text
        getProducts()
    }
}