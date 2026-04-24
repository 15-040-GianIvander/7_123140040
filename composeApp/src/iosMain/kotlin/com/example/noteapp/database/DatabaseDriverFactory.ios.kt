package com.example.noteapp.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.russhwolf.settings.Settings
import com.russhwolf.settings.NSUserDefaultsSettings
import platform.Foundation.NSUserDefaults

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = NotesDatabase.Schema,
            name = "notes.db"
        )
    }

    actual fun createSettings(): Settings {
        return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}