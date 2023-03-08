package com.zaktsy.products.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.zaktsy.products.domain.alarms.ExpirationAlarmService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            ExpirationAlarmService.EXPIRATION_CHANNEL_ID,
            "Expiration",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Used for the expiration notifications"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}