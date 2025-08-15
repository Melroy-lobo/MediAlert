package com.melroy.medialert.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.melroy.medialert.ui.screens.AddEditScreen
import com.melroy.medialert.ui.screens.HomeScreen
import com.melroy.medialert.ui.screens.SettingsScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            val vm: MediViewModel = hiltViewModel()
            HomeScreen(vm, onAdd = { nav.navigate("add") }, onSettings = { nav.navigate("settings") })
        }
        composable("add") {
            val vm: MediViewModel = hiltViewModel()
            AddEditScreen(vm, onBack = { nav.popBackStack() })
        }
        composable("settings") {
            SettingsScreen(onBack = { nav.popBackStack() })
        }
    }
}