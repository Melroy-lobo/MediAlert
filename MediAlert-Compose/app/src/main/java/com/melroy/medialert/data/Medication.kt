package com.melroy.medialert.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Medication(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val dosage: String,
    val timesPerDay: Int,
    val startHour: Int,
    val intervalHours: Int,
    val enabled: Boolean = true
)