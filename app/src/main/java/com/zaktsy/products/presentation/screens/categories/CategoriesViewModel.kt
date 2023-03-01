package com.zaktsy.products.presentation.screens.categories

import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.usecases.categories.AddCategoryUseCase
import com.zaktsy.products.domain.usecases.categories.DeleteCategoryUseCase
import com.zaktsy.products.domain.usecases.categories.EditCategoryUseCase
import com.zaktsy.products.domain.usecases.categories.GetCategoriesUseCase
import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val editCategoryUseCase: EditCategoryUseCase
) : ViewModelWithSearch() {

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val items = getCategoriesUseCase.invoke(_searchedValue.value)
            _categories.value = items
            _isLoading.value = false
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            addCategoryUseCase.invoke(category)
            _categories.value += category
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCategoryUseCase.invoke(category)
            _categories.value -= category
        }
    }

    fun editCategory(editedCategory: Category, newCategoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value -= editedCategory
            editedCategory.name = newCategoryName
            editCategoryUseCase.invoke(editedCategory)
            _categories.value += editedCategory
        }
    }

    override fun onSearchValueChanged(text: String) {
        _searchedValue.value = text
        getCategories()
    }
}