package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.StorageEntity
import com.zaktsy.products.domain.models.Storage

class StorageMapper {
    companion object {
        fun transformFrom(data: StorageEntity): Storage {
            return Storage(data.id, data.name)
        }

        fun transformTo(data: Storage): StorageEntity {
            val storage = StorageEntity(data.name)
            storage.id = data.id

            return storage
        }

        fun transformToStorages(storageEntities: List<StorageEntity>): List<Storage> {
            val storages = ArrayList<Storage>()

            storageEntities.forEach() {
                val storage = transformFrom(it)
                storages.add(storage)
            }

            return storages
        }
    }
}