package com.zaktsy.products.presentation.screens.addproduct

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.alarms.ProductsAlarmScheduler
import com.zaktsy.products.domain.models.*
import com.zaktsy.products.utils.AlarmType
import com.zaktsy.products.domain.usecases.alarms.AddAlarmUseCase
import com.zaktsy.products.domain.usecases.categories.GetCategoriesUseCase
import com.zaktsy.products.domain.usecases.products.AddProductUseCase
import com.zaktsy.products.domain.usecases.producttemplates.AddProductTemplateUseCase
import com.zaktsy.products.domain.usecases.producttemplates.GetProductTemplateUseCase
import com.zaktsy.products.domain.usecases.storages.GetStoragesUseCase
import com.zaktsy.products.presentation.navigation.NavigationRoutes
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
    private val addProductUseCase: AddProductUseCase,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val addProductTemplateUseCase: AddProductTemplateUseCase,
    private val productsAlarmScheduler: ProductsAlarmScheduler,
    private val getProductTemplateUseCase: GetProductTemplateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _saveProductAsTemplate = MutableStateFlow(false)
    val saveProductAsTemplate = _saveProductAsTemplate.asStateFlow()

    fun setSaveProductAsTemplate(value: Boolean) {
        _saveProductAsTemplate.value = value
    }

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

        if (_expirationDuration != 0L) {
            setExpirationDate(Date(_manufactureDate.value.time + _expirationDuration))
        }
    }

    private val _manufactureDateString = MutableStateFlow(_manufactureDate.value.toSimpleString())
    val manufactureDateString = _manufactureDateString.asStateFlow()

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _selectedCategoryName = MutableStateFlow("")
    val selectedCategoryName = _selectedCategoryName.asStateFlow()

    fun setSelectedCategoryName(value: String) {
        _selectedCategoryName.value = value
    }

    private val _storages = MutableStateFlow(emptyList<Storage>())
    val storages = _storages.asStateFlow()

    private val _selectedStorageName = MutableStateFlow("")
    val selectedStorageName = _selectedStorageName.asStateFlow()

    fun setSelectedStorageName(value: String) {
        _selectedStorageName.value = value
    }

    private var _barcode = ""

    private var _expirationDuration = 0L

    private var templateForProductExists = false

    init {
        val argument =
            savedStateHandle.get<String>(NavigationRoutes.AddProductWithBarCodeArg).orEmpty()
        viewModelScope.launch(Dispatchers.IO) {
            getCategories()
            getStorages()
            if (argument.isNotEmpty()) getProductTemplate(argument)
        }
    }

    private suspend fun getProductTemplate(argument: String) {
        val template = getProductTemplateUseCase.invoke(argument)

        if (template.category != null) setSelectedCategoryName(template.category!!.name)
        if (template.name != "") templateForProductExists = true

        _productName.value = template.name
        setExpirationDate(Date(_manufactureDate.value.time + template.expirationDuration))

        _expirationDuration = template.expirationDuration
        _barcode = argument
    }

    private val _expirationDate = MutableStateFlow(Date())

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

    fun addProductAndNotifications(
        selectedCategoryIndex: Int,
        selectedStorageIndex: Int,
        notificationStates: MutableCollection<Pair<AlarmType, MutableState<Boolean>>>
    ) {
        viewModelScope.launch((Dispatchers.IO)) {
            val productId = addProduct(selectedCategoryIndex, selectedStorageIndex)

            for (notificationState in notificationStates) {
                if (notificationState.second.value) {
                    val alarmType = notificationState.first
                    addNotification(alarmType, productId)
                }
            }
        }
    }

    fun saveProductAsTemplate(selectedCategoryIndex: Int) {
        if (!saveProductAsTemplate.value or templateForProductExists) return
        viewModelScope.launch(Dispatchers.IO) {
            val productTemplate = ProductTemplate(
                name = _productName.value,
                barCode = _barcode,
                category = if (selectedCategoryIndex == -1) null else _categories.value[selectedCategoryIndex],
                expirationDuration = _expirationDate.value.time - _manufactureDate.value.time,
            )
            addProductTemplateUseCase.invoke(productTemplate)
        }
    }

    private suspend fun addNotification(alarmType: AlarmType, productId: Long) {
        val expirationDate = _expirationDate.value
        val calender = Calendar.getInstance()
        calender.time = expirationDate

        val daysToExpiration = when (alarmType) {
            AlarmType.ONE_DAY_BEFORE -> {
                calender.add(Calendar.DAY_OF_MONTH, -1)
                1
            }
            AlarmType.TWO_DAYS_BEFORE -> {
                calender.add(Calendar.DAY_OF_MONTH, -2)
                2
            }
            AlarmType.ONE_WEEK_BEFORE -> {
                calender.add(Calendar.DAY_OF_MONTH, -7)
                7
            }
        }
        val dayToNotify = calender.time
        val alarm = ExpirationAlarm(
            productId = productId, daysToExpiration = daysToExpiration, dayToNotify = dayToNotify
        )

        val alarmId = addAlarmUseCase.invoke(alarm)
        alarm.id = alarmId
        productsAlarmScheduler.schedule(alarm)
    }

    private suspend fun addProduct(selectedCategoryIndex: Int, selectedStorageIndex: Int): Long {
        val product = Product(
            name = _productName.value,
            barCode = "",
            category = if (selectedCategoryIndex == -1) null else _categories.value[selectedCategoryIndex],
            storage = if (selectedStorageIndex == -1) null else _storages.value[selectedStorageIndex],
            expirationDuration = _expirationDate.value.time - _manufactureDate.value.time,
            manufactureDate = _manufactureDate.value
        )
        return addProductUseCase.invoke(product)
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