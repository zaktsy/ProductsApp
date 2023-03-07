package com.zaktsy.products.utils.mappers

import com.zaktsy.products.data.local.entities.ExpirationAlarmEntity
import com.zaktsy.products.domain.models.ExpirationAlarm

class AlarmMapper {
    companion object {
        fun transformTo(data: ExpirationAlarm): ExpirationAlarmEntity {
            val alarm =
                ExpirationAlarmEntity(data.productId, data.daysToExpiration, data.dayToNotify)
            alarm.id = data.id

            return alarm
        }
    }
}