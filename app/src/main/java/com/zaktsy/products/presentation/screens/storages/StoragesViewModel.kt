package com.zaktsy.products.presentation.screens.storages

import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.domain.usecases.storages.AddStorageUseCase
import com.zaktsy.products.domain.usecases.storages.DeleteStorageUseCase
import com.zaktsy.products.domain.usecases.storages.EditStorageUseCase
import com.zaktsy.products.domain.usecases.storages.GetStoragesUseCase
import com.zaktsy.products.presentation.screens.ViewModelWithSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoragesViewModel @Inject constructor(
    private val getStoragesUseCase: GetStoragesUseCase,
    private val addStorageUseCase: AddStorageUseCase,
    private val deleteStorageUseCase: DeleteStorageUseCase,
    private val editStorageUseCase: EditStorageUseCase
) : ViewModelWithSearch() {

    private val _storages = MutableStateFlow(emptyList<Storage>())
    val storages = _storages.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        _isLoading.value = true
        getStorages()
    }

    private fun getStorages() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val items = getStoragesUseCase.invoke(_searchedValue.value)
            _storages.value = items
            _isLoading.value = false
        }
    }

    fun addStorage(storage: Storage) {
        viewModelScope.launch(Dispatchers.IO) {
            addStorageUseCase.invoke(storage)
            _storages.value += storage
        }
    }

    fun deleteStorage(storage: Storage) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStorageUseCase.invoke(storage)
            _storages.value -= storage
        }
    }

    fun editStorage(editedStorage: Storage, newStorageName: String) {
        editedStorage.name = newStorageName
        viewModelScope.launch(Dispatchers.IO) {
            editStorageUseCase.invoke(editedStorage)
            _storages.value -= editedStorage
            editedStorage.name = newStorageName
            editStorageUseCase.invoke(editedStorage)
            _storages.value += editedStorage
        }
    }

    override fun onSearchValueChanged() {
        getStorages()
    }
}