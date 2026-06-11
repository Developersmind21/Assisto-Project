package com.example.assisto.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AccentRed

@Composable
fun SosFloatingButton(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { showDialog = true },
        modifier = modifier.size(56.dp),
        shape = CircleShape,
        containerColor = AccentRed,
    ) {
        Icon(Icons.Default.Warning, contentDescription = "SOS", tint = Color.White)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Send SOS Alert?") },
            text = { Text("This will alert your emergency contacts and share your live location. Only use in a real emergency.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onConfirm()
                }) {
                    Text("Send SOS", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            },
        )
    }
}
