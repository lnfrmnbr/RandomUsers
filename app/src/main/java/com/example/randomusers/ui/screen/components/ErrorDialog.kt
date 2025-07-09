package com.example.randomusers.ui.screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(errorMessage: String?, onDismiss: () -> Unit) {
    if (!errorMessage.isNullOrEmpty()) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Ошибка") },
            text = { Text(text = errorMessage) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}