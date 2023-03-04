package com.zaktsy.products.presentation.screens.editscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.domain.usecases.categories.GetCategoriesUseCase
import com.zaktsy.products.domain.usecases.products.EditProductUseCase
import com.zaktsy.products.domain.usecases.products.GetProductUseCase
import com.zaktsy.products.domain.usecases.storages.GetStoragesUseCase
import com.zaktsy.products.presentation.navigation.NavigationRoutes.Companion.EditProductArg
import com.zaktsy.products.utils.DateUtils.Companion.toSimpleString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getStoragesUseCase: GetStoragesUseCase,
    private val editProductUseCase: EditProductUseCase,
    private val getProductUseCase: GetProductUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private lateinit var _product: Product

    private val _productName = MutableStateFlow("")
    val productName = _productName.asStateFlow()

    fun setProductName(value: String) {
        _productName.value = value
    }

    private val _manufactureDate = MutableStateFlow(
        Date.from(
            LocalDate.now().atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant()
        )
    )

    fun setManufactureDate(value: Date) {
        _manufactureDate.value = value
        _manufactureDateString.value = value.toSimpleString()
    }

    private val _manufactureDateString = MutableStateFlow(_manufactureDate.value.toSimpleString())
    val manufactureDateString = _manufactureDateString.asStateFlow()

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _selectedCategoryName = MutableStateFlow("")
    val selectedCategoryName = _selectedCategoryName.asStateFlow()

    fun setSelectedCategoryName(value: String){
        _selectedCategoryName.value = value
    }

    private val _storages = MutableStateFlow(emptyList<Storage>())
    val storages = _storages.asStateFlow()

    private val _selectedStorageName = MutableStateFlow("")
    val selectedStorageName = _selectedStorageName.asStateFlow()

    fun setSelectedStorageName(value: String){
        _selectedStorageName.value = value
    }

    init {
        val argument = savedStateHandle.get<String>(EditProductArg).orEmpty()
        viewModelScope.launch((Dispatchers.IO)) {
            getProduct(argument.toLong())
            getCategories()
            getStorages()
        }
    }

    private suspend fun getProduct(selectedProductId: Long) {
        val product = getProductUseCase.invoke(selectedProductId)
        _product = product
        _productName.value = product.name
        _manufactureDate.value = product.manufactureDate
        _expirationDate.value = Date(product.manufactureDate.time + product.expirationDuration)

        if (product.category != null)
            setSelectedCategoryName(product.category!!.name)

        if (product.storage != null)
            setSelectedStorageName(product.storage!!.name)
    }

    private val _expirationDate = MutableStateFlow(
        Date.from(
            LocalDate.now().atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant()
        )
    )

    fun setExpirationDate(value: Date) {
        if (value < _manufactureDate.value) {
            _expirationDate.value = _manufactureDate.value
            _expirationDateString.value = _manufactureDate.value.toSimpleString()
        } else {
            _expirationDate.value = value
            _expirationDateString.value = value.toSimpleString()
        }
    }

    private val _expirationDateString = MutableStateFlow(_expirationDate.value.toSimpleString())
    val expirationDateString = _expirationDateString.asStateFlow()

    fun saveProduct(selectedCategoryIndex: Int, selectedStorageIndex: Int) {
        viewModelScope.launch((Dispatchers.IO)) {
            _product.name = _productName.value
            _product.category =
                if (selectedCategoryIndex == -1) _product.category else _categories.value[selectedCategoryIndex]
            _product.storage =
                if (selectedStorageIndex == -1) _product.storage else _storages.value[selectedStorageIndex]
            _product.expirationDuration = _expirationDate.value.time - _manufactureDate.value.time
            _product.manufactureDate = _manufactureDate.value
            editProductUseCase.invoke(_product)
        }
    }

    private suspend fun getCategories() {
        val items = getCategoriesUseCase.invoke("")
        _categories.value = items
    }

    private suspend fun getStorages() {
        val items = getStoragesUseCase.invoke("")
        _storages.value = items
    }
}