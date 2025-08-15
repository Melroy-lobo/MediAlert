package com.melroy.medialert.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melroy.medialert.data.Medication
import com.melroy.medialert.data.repo.MedRepo
import com.melroy.medialert.util.AlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediViewModel @Inject constructor(
    private val repo: MedRepo,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    val meds = repo.observeMeds().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addOrUpdate(med: Medication, onDone: () -> Unit) {
        viewModelScope.launch {
            repo.upsert(med)
            scheduler.scheduleNextDose(med)
            onDone()
        }
    }

    fun delete(med: Medication) {
        viewModelScope.launch {
            repo.delete(med)
            scheduler.cancel(med)
        }
    }
}