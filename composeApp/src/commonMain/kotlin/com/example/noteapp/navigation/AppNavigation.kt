package com.example.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.noteapp.viewmodel.NotesViewModel
import com.example.noteapp.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: NotesViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Notes.route) {
        composable(Screen.Notes.route) {
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id.toInt())) },
                onAddClick = { navController.navigate(Screen.AddNote.route) }
            )
        }
        composable(Screen.AddNote.route) {
            AddNoteScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("noteId") ?: 0L
            NoteDetailScreen(
                noteId = id,
                viewModel = viewModel,
                onEditClick = { navController.navigate(Screen.EditNote.createRoute(id.toInt())) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("noteId") ?: 0L
            EditNoteScreen(noteId = id, viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = viewModel,
                onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id.toInt())) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = viewModel)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(viewModel = viewModel)
        }
    }
}