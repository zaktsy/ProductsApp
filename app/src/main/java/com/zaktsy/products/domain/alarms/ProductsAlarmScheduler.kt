package com.zaktsy.products.domain.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.zaktsy.products.domain.models.ExpirationAlarm
import com.zaktsy.products.domain.usecases.products.GetProductUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsAlarmScheduler @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val getProductUseCase: GetProductUseCase
) : AlarmScheduler {

    private val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun schedule(alarm: ExpirationAlarm) {
        val product = getProductUseCase.invoke(alarm.productId)
        val intent = Intent(appContext, AlarmReceiver::class.java).apply {

            putExtra("product_name", product.name)
            putExtra("days_to_expiration", alarm.daysToExpiration.toString())
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, alarm.dayToNotify.time, PendingIntent.getBroadcast(
                appContext,
                alarm.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarm: ExpirationAlarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                appContext,
                alarm.hashCode(),
                Intent(appContext, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}