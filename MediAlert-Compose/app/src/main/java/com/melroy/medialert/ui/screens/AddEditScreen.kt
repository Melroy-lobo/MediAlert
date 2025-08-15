package com.melroy.medialert.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.melroy.medialert.data.Medication
import com.melroy.medialert.ui.MediViewModel

@Composable
fun AddEditScreen(vm: MediViewModel, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var times by remember { mutableStateOf("1") }
    var start by remember { mutableStateOf("8") }
    var interval by remember { mutableStateOf("8") }

    Scaffold(topBar = { TopAppBar(title = { Text("Add Medication") }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage (e.g., 1 tablet)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = times, onValueChange = { times = it.filter { c -> c.isDigit() } }, label = { Text("Times per day") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = start, onValueChange = { start = it.filter { c -> c.isDigit() } }, label = { Text("First dose hour (0-23)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = interval, onValueChange = { interval = it.filter { c -> c.isDigit() } }, label = { Text("Interval hours") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    val med = Medication(
                        name = name.trim(),
                        dosage = dosage.trim(),
                        timesPerDay = times.toIntOrNull() ?: 1,
                        startHour = start.toIntOrNull() ?: 8,
                        intervalHours = interval.toIntOrNull() ?: 8,
                        enabled = true
                    )
                    vm.addOrUpdate(med) { onBack() }
                },
                enabled = name.isNotBlank()
            ) { Text("Save") }
        }
    }
}