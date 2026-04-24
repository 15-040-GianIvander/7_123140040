package com.example.noteapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.components.BottomBar
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.database.DatabaseDriverFactory
import com.example.noteapp.database.NotesDatabase
import com.example.noteapp.navigation.AppNavigation
import com.example.noteapp.viewmodel.NotesViewModel

// Sky Blue Palette
val SkyBluePrimary = Color(0xFF03A9F4)
val KeepPrimary = SkyBluePrimary
val SkyBlueOnPrimary = Color.White
val SkyBluePrimaryContainer = Color(0xFFB3E5FC)
val SkyBlueOnPrimaryContainer = Color(0xFF01579B)

private val SkyBlueLightColorScheme = lightColorScheme(
    primary = SkyBluePrimary,
    onPrimary = SkyBlueOnPrimary,
    primaryContainer = SkyBluePrimaryContainer,
    onPrimaryContainer = SkyBlueOnPrimaryContainer,
    background = Color(0xFFF0F8FF), // AliceBlue
    surface = Color.White,
)

private val SkyBlueDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81D4FA),
    onPrimary = Color(0xFF003546),
    primaryContainer = Color(0xFF004D65),
    onPrimaryContainer = Color(0xFFB3E5FC),
    background = Color(0xFF001F2A),
    surface = Color(0xFF001F2A),
)

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val settings = remember { driverFactory.createSettings() }
    val settingsManager = remember { SettingsManager(settings) }
    val database = remember { NotesDatabase(driverFactory.createDriver()) }
    val repository = remember { NoteRepository(database) }
    val viewModel = remember { NotesViewModel(repository, settingsManager) }
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    MaterialTheme(
        colorScheme = if (isDarkMode) SkyBlueDarkColorScheme else SkyBlueLightColorScheme
    ) {
        val navController = rememberNavController()

        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                bottomBar = {
                    BottomBar(navController = navController)
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AppNavigation(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
