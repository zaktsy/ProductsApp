package com.zaktsy.products.domain.usecases.alarms

import com.zaktsy.products.domain.models.Category
import com.zaktsy.products.domain.models.ExpirationAlarm
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(alarm: ExpirationAlarm) = repository.addAlarm(alarm)
}

class AddCategoryUseCase @Inject constructor(private val repository: ProductsRepository){
    suspend operator fun invoke(category: Category) = repository.addCategory(category)
}