package com.zaktsy.products.domain.usecases.storages

import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class EditStorageUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(storage: Storage) = repository.editStorage(storage)
}