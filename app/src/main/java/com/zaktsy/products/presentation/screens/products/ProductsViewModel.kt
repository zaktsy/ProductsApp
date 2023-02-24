package com.zaktsy.products.presentation.screens.products

import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(): ViewModelWithSearch() {

    private val _selectedDisplayMode = MutableStateFlow(0)
    val selectedDisplayMode = _selectedDisplayMode.asStateFlow()

    fun setSelectedDisplayMode(value: Int){
        _selectedDisplayMode.value = value
    }

    private val _selectedSortOrder = MutableStateFlow(0)
    val selectedSortOrder = _selectedSortOrder.asStateFlow()

    fun setSelectedSortOrder(value: Int){
        _selectedSortOrder.value = value
    }

    override fun onSearchValueChanged() {

    }
}