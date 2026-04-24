package com.example.noteapp.navigation

sealed class Screen(val route: String, val title: String) {
    object Notes : Screen("notes", "Notes")
    object Favorites : Screen("favorites", "Favorites")
    object Profile : Screen("profile", "Profile")
    object Settings : Screen("settings", "Settings")
    object AddNote : Screen("add_note", "Add Note")
    object NoteDetail : Screen("note_detail/{noteId}", "Detail") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object EditNote : Screen("edit_note/{noteId}", "Edit") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
}