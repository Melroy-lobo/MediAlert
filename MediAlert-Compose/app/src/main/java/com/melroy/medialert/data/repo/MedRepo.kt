package com.melroy.medialert.data.repo

import com.melroy.medialert.data.Medication
import com.melroy.medialert.data.db.MedDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedRepo @Inject constructor(private val dao: MedDao) {
    fun observeMeds(): Flow<List<Medication>> = dao.observeAll()
    suspend fun upsert(m: Medication) = dao.upsert(m)
    suspend fun delete(m: Medication) = dao.delete(m)
}