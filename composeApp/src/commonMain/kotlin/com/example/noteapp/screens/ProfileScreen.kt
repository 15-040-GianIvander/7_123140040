package com.example.noteapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.KeepPrimary
import com.example.noteapp.viewmodel.NotesViewModel

@Composable
fun ProfileScreen(viewModel: NotesViewModel) {
    var isEditMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Gian") }
    var role by remember { mutableStateOf("Informatics Student") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )

            if (isEditMode) {
                TextButton(onClick = { isEditMode = false }) {
                    Text("Save", fontWeight = FontWeight.Bold, color = KeepPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(KeepPrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            // Menggunakan simbol teks sesuai permintaan agar tidak error library
            Text(
                text = "👤",
                fontSize = 50.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isEditMode) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Role") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )
        } else {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            AssistChip(
                onClick = { isEditMode = true },
                label = { Text("Edit Profile", color = KeepPrimary) },
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f)
                ),
                shape = CircleShape
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "ITERA Academic Account",
            style = MaterialTheme.typography.labelSmall,
            color = Color.LightGray
        )
    }
}
