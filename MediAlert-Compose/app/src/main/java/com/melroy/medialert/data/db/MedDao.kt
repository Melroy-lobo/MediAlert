package com.melroy.medialert.data.db

import androidx.room.*
import com.melroy.medialert.data.Medication
import kotlinx.coroutines.flow.Flow

@Dao
interface MedDao {
    @Query("SELECT * FROM Medication ORDER BY name ASC")
    fun observeAll(): Flow<List<Medication>>

    @Query("SELECT * FROM Medication ORDER BY name ASC")
    suspend fun getAllOnce(): List<Medication>

    @Upsert
    suspend fun upsert(med: Medication)

    @Delete
    suspend fun delete(med: Medication)

    @Query("SELECT * FROM Medication WHERE id = :id")
    suspend fun getById(id: String): Medication?
}