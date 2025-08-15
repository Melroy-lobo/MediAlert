package com.melroy.medialert.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.melroy.medialert.data.Medication
import com.melroy.medialert.ui.MediViewModel

@Composable
fun HomeScreen(vm: MediViewModel, onAdd: () -> Unit, onSettings: () -> Unit) {
    val meds by vm.meds.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MediAlert") }, actions = {
                TextButton(onClick = onSettings) { Text("Settings") }
            })
        },
        floatingActionButton = { FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, null) } }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            if (meds.isEmpty()) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("No medications yet.")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onAdd) { Text("Add your first medication") }
                }
            } else {
                LazyColumn(Modifier.fillMaxSize().padding(12.dp)) {
                    items(meds, key = { it.id }) { med ->
                        ElevatedCard(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                            Column(Modifier.padding(16.dp)) {
                                Text(med.name, style = MaterialTheme.typography.titleMedium)
                                Text("Dosage: ${med.dosage}")
                                Text("Times/day: ${med.timesPerDay}, start: ${med.startHour}:00, every ${med.intervalHours}h")
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    TextButton(onClick = { vm.delete(med) }) { Text("Delete") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}