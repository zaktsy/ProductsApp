package com.zaktsy.products.domain.usecases.storages

import com.zaktsy.products.domain.models.Storage
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetStoragesUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(name: String): List<Storage> {
        if (name == "") return repository.getAllStorages()

        return repository.getStorages(name)
    }
}