package com.zaktsy.products.presentation.screens.categories

import androidx.compose.runtime.Immutable
import com.zaktsy.products.domain.models.Category

@Immutable
sealed interface CategoriesUiState {
    data class Success(val items: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}