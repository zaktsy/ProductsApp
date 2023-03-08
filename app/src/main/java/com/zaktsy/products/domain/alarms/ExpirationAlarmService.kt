package com.zaktsy.products.domain.alarms

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.zaktsy.products.R

class ExpirationAlarmService(
    private val appContext: Context,
) {
    private val notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(productName: String, daysToExpiration: String) {
        val notification = NotificationCompat.Builder(appContext, EXPIRATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(appContext.getString(R.string.product_expiration)).setContentText(
                String.format(
                    appContext.getString(R.string.product_expiration_text),
                    productName,
                    daysToExpiration
                )
            ).build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val EXPIRATION_CHANNEL_ID = "expiration_channel"
    }
}