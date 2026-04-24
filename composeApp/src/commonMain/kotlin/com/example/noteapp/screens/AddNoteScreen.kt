package com.example.noteapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.noteapp.KeepPrimary
// 1. Tambahin import ViewModel
import com.example.noteapp.viewmodel.NotesViewModel

@Composable
fun AddNoteScreen(
    viewModel: NotesViewModel, // 2. Inject ViewModel ke parameter screen
    onBack: () -> Unit
) {
    // State lokal buat nampung ketikan teks user sebelum di-save
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("Cancel", color = Color.Gray)
            }

            Text(
                text = "New Note",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            TextButton(
                onClick = {
                    // 3. Logika Save ganti pakai ViewModel
                    if (title.isNotBlank()) {
                        // Nggak perlu lagi mikirin ngitung ID manual kayak kemarin,
                        // Database SQLite (SQLDelight) otomatis bikinin ID baru (AUTOINCREMENT)
                        viewModel.addNote(title, content)
                        onBack()
                    }
                }
            ) {
                Text("Save", fontWeight = FontWeight.Bold, color = KeepPrimary)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text("Title", style = MaterialTheme.typography.headlineSmall.copy(color = Color.LightGray))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = {
                    Text("Note content...", style = MaterialTheme.typography.bodyLarge.copy(color = Color.LightGray))
                },
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}