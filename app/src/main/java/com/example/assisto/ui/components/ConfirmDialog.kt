package com.example.assisto.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.assisto.ui.theme.AssistoTokens

/**
 * Confirmation dialog. The confirm button is danger-styled when [isDangerous].
 */
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    cancelLabel: String = "Cancel",
    isDangerous: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, style = MaterialTheme.typography.headlineSmall) },
        text = { Text(message, style = MaterialTheme.typography.bodyMedium) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    confirmLabel,
                    color = if (isDangerous) AssistoTokens.Colors.AccentRed else AssistoTokens.Colors.PrimaryBlue,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(cancelLabel, color = AssistoTokens.Colors.TextSecondary)
            }
        },
    )
}

/** Alias kept for spec naming. */
@Composable
fun AssistoConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    cancelLabel: String = "Cancel",
    isDangerous: Boolean = false,
) = ConfirmDialog(title, message, confirmLabel, onConfirm, onDismiss, cancelLabel, isDangerous)
