package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.noteapp.database.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // 1. Buat driver factory khusus Android (butuh context 'this')
        val driverFactory = DatabaseDriverFactory(this)

        setContent {
            // 2. Oper driver factory ke fungsi App
            App(driverFactory = driverFactory)
        }
    }
}