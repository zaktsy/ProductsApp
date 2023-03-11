package com.zaktsy.products.presentation.screens.templates

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.ProductTemplate
import com.zaktsy.products.domain.usecases.producttemplates.DeleteProductTemplateUseCase
import com.zaktsy.products.domain.usecases.producttemplates.GetProductTemplatesUseCase
import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor(
    private val getProductTemplatesUseCase: GetProductTemplatesUseCase,
    private val deleteProductTemplateUseCase: DeleteProductTemplateUseCase,
) : ViewModelWithSearch() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _productTemplates = MutableStateFlow(mutableStateListOf<ProductTemplate>())
    var productTemplates = _productTemplates.asStateFlow()

    override fun onSearchValueChanged(text: String) {
        _searchedValue.value = text
        getTemplates()
    }

    init {
        getTemplates()
    }

    fun getTemplates() {
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.value = true
            val items = getProductTemplatesUseCase.invoke(_searchedValue.value)
            _productTemplates.value = mutableStateListOf()
            _productTemplates.value.addAll(items)
            _isLoading.value = false
        }
    }

    fun deleteTemplate(productTemplate: ProductTemplate) {
        _productTemplates.value.removeIf { it.id == productTemplate.id }
        viewModelScope.launch(Dispatchers.IO) {
            deleteProductTemplateUseCase.invoke(productTemplate)
        }
    }
}