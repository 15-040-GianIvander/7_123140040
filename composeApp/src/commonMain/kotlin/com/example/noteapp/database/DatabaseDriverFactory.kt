package com.example.noteapp.database

import app.cash.sqldelight.db.SqlDriver
import com.russhwolf.settings.Settings

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
    fun createSettings(): Settings
}