package com.zaktsy.products.presentation.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ViewModelWithSearch(): ViewModel() {

    protected val _searchedValue = MutableStateFlow("")
    val searchedValue = _searchedValue.asStateFlow()

    fun setSearchedValue(value: String){
        _searchedValue.value = value
    }

    abstract fun onSearchValueChanged()
}