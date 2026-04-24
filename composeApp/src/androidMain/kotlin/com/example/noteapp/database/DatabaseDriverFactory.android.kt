package com.example.noteapp.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = NotesDatabase.Schema,
            context = context,
            name = "notes.db"
        )
    }

    actual fun createSettings(): Settings {
        val sharedPrefs = context.getSharedPreferences("notes_settings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(sharedPrefs)
    }
}