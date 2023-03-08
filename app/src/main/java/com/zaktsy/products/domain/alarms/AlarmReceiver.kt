package com.zaktsy.products.domain.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val productName = intent?.getStringExtra("product_name") ?: return
        val daysToExpiration = intent.getStringExtra("days_to_expiration") ?: return

        context?.let { ExpirationAlarmService(it) }?.showNotification(productName, daysToExpiration)
    }
}