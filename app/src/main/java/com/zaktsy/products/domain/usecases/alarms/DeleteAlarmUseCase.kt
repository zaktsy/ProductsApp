package com.zaktsy.products.domain.usecases.alarms

import com.zaktsy.products.domain.models.ExpirationAlarm
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(alarm: ExpirationAlarm) = repository.removeAlarm(alarm)
}