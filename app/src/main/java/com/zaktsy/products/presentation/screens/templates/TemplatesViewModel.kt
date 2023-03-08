package com.zaktsy.products.presentation.screens.templates

import androidx.compose.runtime.mutableStateListOf
import com.zaktsy.products.domain.models.ProductTemplate
import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor(

) : ViewModelWithSearch() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _productTemplates = MutableStateFlow(mutableStateListOf<ProductTemplate>())
    var productTemplates = _productTemplates.asStateFlow()

    override fun onSearchValueChanged(text: String) {
        _searchedValue.value = text
    }

    fun getTemplates() {

    }

    fun deleteTemplate(productTemplate: ProductTemplate) {

    }
}