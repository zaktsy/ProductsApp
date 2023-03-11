package com.zaktsy.products.presentation.screens.edittemplate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.ProductTemplate
import com.zaktsy.products.domain.usecases.categories.GetCategoriesUseCase
import com.zaktsy.products.domain.usecases.producttemplates.EditProductTemplateUseCase
import com.zaktsy.products.domain.usecases.producttemplates.GetProductTemplateUseCase
import com.zaktsy.products.presentation.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class EditTemplateViewModel @Inject constructor(
    private val getProductTemplateUseCase: GetProductTemplateUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val editProductTemplateUseCase: EditProductTemplateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var _productTemplate: ProductTemplate

    private val _productTemplateName = MutableStateFlow("")
    val productTemplateName = _productTemplateName.asStateFlow()

    fun setProductTemplateName(value: String) {
        _productTemplateName.value = value
    }

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _selectedCategoryName = MutableStateFlow("")
    val selectedCategoryName = _selectedCategoryName.asStateFlow()

    fun setSelectedCategoryName(value: String) {
        _selectedCategoryName.value = value
    }

    private val _barCode = MutableStateFlow("")
    val barCode = _barCode.asStateFlow()

    fun setBarCode(value: String) {
        _barCode.value = value
    }

    private val _expirationDuration = MutableStateFlow("")
    val expirationDuration = _expirationDuration.asStateFlow()

    private val _expirationDurationMilliseconds = MutableStateFlow(0L)

    fun setExpirationDuration(value: String) {
        val daysCount = value.toLong()
        _expirationDurationMilliseconds.value = TimeUnit.DAYS.toMillis(daysCount)
        _expirationDuration.value = daysCount.toString()
    }

    fun saveTemplate(selectedCategoryIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _productTemplate.name = _productTemplateName.value
            _productTemplate.category =
                if (selectedCategoryIndex == -1) _productTemplate.category else _categories.value[selectedCategoryIndex]
            _productTemplate.expirationDuration = _expirationDurationMilliseconds.value
            _productTemplate.barCode = _barCode.value
            editProductTemplateUseCase.invoke(_productTemplate)
        }
    }

    init {
        val argument = savedStateHandle.get<String>(NavigationRoutes.EditTemplateArg).orEmpty()
        viewModelScope.launch((Dispatchers.IO)) {
            getProductTemplate(argument.toLong())
            getCategories()
        }
    }

    private suspend fun getProductTemplate(productTemplateId: Long) {
        val template = getProductTemplateUseCase.invoke(productTemplateId)

        _productTemplate = template
        _productTemplateName.value = template.name
        _barCode.value = template.barCode
        _expirationDuration.value = TimeUnit.MILLISECONDS.toDays(template.expirationDuration).toString()
        _expirationDurationMilliseconds.value = template.expirationDuration

        if (template.category != null) setSelectedCategoryName(template.category!!.name)
    }

    private suspend fun getCategories() {
        val items = getCategoriesUseCase.invoke("")
        _categories.value = items
    }
}