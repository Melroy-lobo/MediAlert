package com.melroy.medialert.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.melroy.medialert.MainActivity
import com.melroy.medialert.R
import com.melroy.medialert.data.Medication

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val name = intent?.getStringExtra("name") ?: "Medication"
        val dosage = intent?.getStringExtra("dosage") ?: ""
        val channelId = "medialert_channel"

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(channelId, "Medication Reminders", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Alerts for scheduled medications"
                enableLights(true); lightColor = Color.MAGENTA
                enableVibration(true)
            }
            nm.createNotificationChannel(ch)
        }

        val contentIntent = PendingIntent.getActivity(
            context, 0, Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_pill)
            .setContentTitle("Time to take: $name")
            .setContentText("Dosage: $dosage")
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        nm.notify(System.currentTimeMillis().toInt(), notification)

        // Reschedule next approximate occurrence
        val med = Medication(
            id = intent?.getStringExtra("medId") ?: "temp",
            name = name,
            dosage = dosage,
            timesPerDay = intent?.getIntExtra("timesPerDay", 1) ?: 1,
            startHour = intent?.getIntExtra("startHour", 8) ?: 8,
            intervalHours = intent?.getIntExtra("interval", 8) ?: 8,
            enabled = true
        )
        AlarmScheduler(context).scheduleNextDose(med)
    }
}