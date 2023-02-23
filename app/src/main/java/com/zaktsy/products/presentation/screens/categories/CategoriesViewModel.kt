package com.zaktsy.products.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.usecases.categories.AddCategoryUseCase
import com.zaktsy.products.domain.usecases.categories.GetCategoriesUseCase
import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesScreenUiState(
    val categoriesState: CategoriesUiState
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModelWithSearch() {

    private val _uiState = MutableStateFlow(CategoriesScreenUiState(CategoriesUiState.Loading))
    val uiState = _uiState.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = CategoriesScreenUiState(CategoriesUiState.Loading)
            val items = getCategoriesUseCase.invoke(_searchedValue.value)
            if (items.isNotEmpty()) {
                _uiState.value = CategoriesScreenUiState(CategoriesUiState.Success(items))
            } else {
                _uiState.value = CategoriesScreenUiState(CategoriesUiState.Error)
            }
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            addCategoryUseCase.invoke(category)
            getCategories()
        }
    }

    override fun onSearchValueChanged() {
        getCategories()
    }
}