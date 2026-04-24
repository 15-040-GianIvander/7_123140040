package com.example.noteapp.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import com.russhwolf.settings.get

class SettingsManager(private val settings: Settings) {
    // Sesuai latihan 1 modul [cite: 412]
    var isDarkMode: Boolean
        get() = settings["is_dark_mode", false]
        set(value) { settings["is_dark_mode"] = value }
}