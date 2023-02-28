package com.zaktsy.products.presentation.screens.addproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.Product
import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.domain.usecases.categories.GetCategoriesUseCase
import com.zaktsy.products.domain.usecases.products.AddProductUseCase
import com.zaktsy.products.domain.usecases.storages.GetStoragesUseCase
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
class AddProductViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getStoragesUseCase: GetStoragesUseCase,
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {

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
        _expirationDate.value = value
        _manufactureDateString.value = value.toSimpleString()
    }

    private val _manufactureDateString = MutableStateFlow(_manufactureDate.value.toSimpleString())
    val manufactureDateString = _manufactureDateString.asStateFlow()

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _storages = MutableStateFlow(emptyList<Storage>())
    val storages = _storages.asStateFlow()

    init {
        getCategories()
        getStorages()
    }

    private val _expirationDate = MutableStateFlow(
        Date.from(
            LocalDate.now().atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant()
        )
    )

    fun setExpirationDate(value: Date) {
        _expirationDate.value = value
        _expirationDateString.value = value.toSimpleString()
    }

    private val _expirationDateString = MutableStateFlow(_expirationDate.value.toSimpleString())
    val expirationDateString = _expirationDateString.asStateFlow()

    fun addProduct(selectedCategoryIndex: Int, selectedStorageIndex: Int) {
        viewModelScope.launch((Dispatchers.IO)) {
            val product = Product(
                name = _productName.value,
                barCode = "",
                category = if (selectedCategoryIndex == -1) null else _categories.value[selectedCategoryIndex],
                storage = if (selectedStorageIndex == -1) null else _storages.value[selectedStorageIndex],
                expirationDuration = _expirationDate.value.time - _manufactureDate.value.time,
                manufactureDate = _manufactureDate.value
            )
            addProductUseCase.invoke(product)
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = getCategoriesUseCase.invoke("")
            _categories.value = items
        }
    }

    private fun getStorages() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = getStoragesUseCase.invoke("")
            _storages.value = items
        }
    }
}