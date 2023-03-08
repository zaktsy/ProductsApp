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

        fun transformToAlarms(alarmEntities: List<ExpirationAlarmEntity>): List<ExpirationAlarm> {
            val alarms = ArrayList<ExpirationAlarm>()

            alarmEntities.forEach {
                val alarm = transformFrom(it)
                alarms.add(alarm)
            }

            return alarms
        }

        private fun transformFrom(data: ExpirationAlarmEntity): ExpirationAlarm {
            return ExpirationAlarm(data.id, data.productId, data.daysToExpiration, data.dayToNotify)
        }
    }
}