package com.melroy.medialert.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.melroy.medialert.data.Medication

@Database(entities = [Medication::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun medDao(): MedDao
}