package com.melroy.medialert.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.melroy.medialert.data.Medication
import java.util.Calendar

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleNextDose(med: Medication) {
        cancel(med)
        if (!med.enabled) return

        val now = Calendar.getInstance()
        val times = (0 until med.timesPerDay).map { i -> (med.startHour + i * med.intervalHours) % 24 }

        // Choose the next occurrence today or tomorrow
        var triggerHour = times.find { it > now.get(Calendar.HOUR_OF_DAY) } ?: times.firstOrNull() ?: med.startHour
        val trigger = Calendar.getInstance().apply {
            set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR_OF_DAY, triggerHour)
            if (get(Calendar.HOUR_OF_DAY) <= now.get(Calendar.HOUR_OF_DAY)) add(Calendar.DAY_OF_YEAR, 1)
        }

        val pi = pendingIntent(med)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger.timeInMillis, pi)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger.timeInMillis, pi)
        }
    }

    fun cancel(med: Medication) {
        alarmManager.cancel(pendingIntent(med))
    }

    private fun pendingIntent(med: Medication): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("medId", med.id)
            putExtra("name", med.name)
            putExtra("dosage", med.dosage)
            putExtra("interval", med.intervalHours)
            putExtra("timesPerDay", med.timesPerDay)
            putExtra("startHour", med.startHour)
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(context, med.id.hashCode(), intent, flags)
    }
}