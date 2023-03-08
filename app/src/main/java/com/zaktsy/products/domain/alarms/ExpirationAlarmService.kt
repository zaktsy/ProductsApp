package com.zaktsy.products.domain.alarms

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.zaktsy.products.R
import com.zaktsy.products.presentation.MainActivity

class ExpirationAlarmService(
    private val appContext: Context,
) {
    private val notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(productName: String, daysToExpiration: String) {

        val notificationIntent = Intent(appContext, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            appContext,
            0,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(appContext, EXPIRATION_CHANNEL_ID)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(appContext.getString(R.string.product_expiration)).setContentText(
                String.format(
                    appContext.getString(R.string.product_expiration_text),
                    productName,
                    daysToExpiration
                )
            ).build()

        notificationManager.notify(0, notification)
    }

    companion object {
        const val EXPIRATION_CHANNEL_ID = "expiration_channel"
    }
}