package com.zaktsy.products.domain.models

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class Product(
    val id: Long = 0,
    var name: String,
    var expirationDuration: Long,
    var barCode: String,
    var category: Category?,
    var storage: Storage?,
    var manufactureDate: Date
) {
    val percentageDueExpiration: Float = calculatePercentage()
    var daysDueExpiration: Long = 0

    private fun calculatePercentage(): Float {
        val now = Date.from(
            LocalDate.now().atStartOfDay().atZone(
                ZoneId.systemDefault()
            ).toInstant()
        ).time

        val timeDueExpiration = expirationDuration - (now - manufactureDate.time)
        daysDueExpiration = timeDueExpiration / (1000 * 60 * 60 * 24)

        var percentageDueExpiration = 0f
        if (expirationDuration != 0L){
            percentageDueExpiration = timeDueExpiration.toFloat() / expirationDuration.toFloat()
        }

        return percentageDueExpiration
    }
}