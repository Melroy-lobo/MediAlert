package com.melroy.medialert.util

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.room.Room
import com.melroy.medialert.data.db.AppDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RescheduleService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(applicationContext, AppDb::class.java, "medialert.db").build()
            val meds = db.medDao().getAllOnce()
            val scheduler = AlarmScheduler(applicationContext)
            meds.forEach { scheduler.scheduleNextDose(it) }
            stopSelf()
        }
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
}