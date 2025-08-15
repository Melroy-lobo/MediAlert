package com.melroy.medialert.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action?.contains("BOOT_COMPLETED") == true || intent?.action?.contains("LOCKED_BOOT_COMPLETED") == true) {
            // Kick off a lightweight service that reads DB and re-schedules all meds
            context.startService(Intent(context, RescheduleService::class.java))
        }
    }
}