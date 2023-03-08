package com.zaktsy.products.domain.usecases.alarms

import com.zaktsy.products.domain.models.ExpirationAlarm
import com.zaktsy.products.domain.repository.ProductsRepository
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(productId: Long): List<ExpirationAlarm> {
        return repository.getAlarms(productId)
    }
}