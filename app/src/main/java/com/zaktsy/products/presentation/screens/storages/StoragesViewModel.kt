package com.zaktsy.products.presentation.screens.storages

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.zaktsy.products.domain.models.Category
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

    private val _storages = MutableStateFlow(mutableStateListOf<Storage>())
    val storages = _storages.asStateFlow()

    private val _allStorages = MutableStateFlow(mutableStateListOf<Storage>())
    val allStorages = _allStorages.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        getStorages()
        getAllStorages()
    }

    private fun getStorages() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val items = getStoragesUseCase.invoke(_searchedValue.value)
            _storages.value = mutableStateListOf()
            _storages.value.addAll(items)
            _isLoading.value = false
        }
    }

    private fun getAllStorages(){
        viewModelScope.launch(Dispatchers.IO){
            val allStorages = getStoragesUseCase.invoke("")
            _allStorages.value = mutableStateListOf()
            _allStorages.value.addAll(allStorages)
        }
    }

    fun addStorage(storage: Storage) {
        viewModelScope.launch(Dispatchers.IO) {
            addStorageUseCase.invoke(storage)
            getStorages()
            getAllStorages()
        }
    }

    fun deleteStorage(storage: Storage) {
        _storages.value.removeIf { it.id == storage.id }
        _allStorages.value.removeIf { it.id == storage.id }
        viewModelScope.launch(Dispatchers.IO) {
            deleteStorageUseCase.invoke(storage)
            _storages.value -= storage
        }
    }

    fun editStorage(editedStorage: Storage, newStorageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            editStorageUseCase.invoke(editedStorage)
            _storages.value -= editedStorage
            editedStorage.name = newStorageName
            editStorageUseCase.invoke(editedStorage)
            getStorages()
            getAllStorages()
        }
    }

    override fun onSearchValueChanged(text: String) {
        _searchedValue.value = text
        getStorages()
    }
}