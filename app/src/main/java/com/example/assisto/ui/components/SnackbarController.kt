package com.example.assisto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AssistoTokens
import kotlinx.coroutines.flow.MutableSharedFlow

enum class SnackbarType { SUCCESS, ERROR, INFO, WARNING }

data class SnackbarEvent(val message: String, val type: SnackbarType)

/** App-wide snackbar dispatcher. Host with [SnackbarHostListener] in the root. */
object SnackbarController {
    val events = MutableSharedFlow<SnackbarEvent>(extraBufferCapacity = 1)

    suspend fun show(message: String, type: SnackbarType = SnackbarType.INFO) {
        events.emit(SnackbarEvent(message, type))
    }
}

/** Collects [SnackbarController] events and forwards them to a Material [SnackbarHostState]. */
@Composable
fun SnackbarHostListener(hostState: SnackbarHostState) {
    LaunchedEffect(Unit) {
        SnackbarController.events.collect { event ->
            hostState.showSnackbar(TypedSnackbarVisuals(event.message, event.type))
        }
    }
}

private class TypedSnackbarVisuals(
    override val message: String,
    val type: SnackbarType,
) : SnackbarVisuals {
    override val actionLabel: String? = null
    override val withDismissAction: Boolean = false
    override val duration: SnackbarDuration = SnackbarDuration.Short
}

/** Styled snackbar: colored left border + icon matching the type, white bg, TextPrimary message. */
@Composable
fun AssistoStyledSnackbar(data: androidx.compose.material3.SnackbarData) {
    val type = (data.visuals as? TypedSnackbarVisuals)?.type ?: SnackbarType.INFO
    val (accent, icon: ImageVector) = when (type) {
        SnackbarType.SUCCESS -> AssistoTokens.Colors.SuccessGreen to Icons.Filled.CheckCircle
        SnackbarType.ERROR -> AssistoTokens.Colors.ErrorRed to Icons.Filled.Error
        SnackbarType.WARNING -> AssistoTokens.Colors.WarningOrange to Icons.Filled.Warning
        SnackbarType.INFO -> AssistoTokens.Colors.SecondaryBlue to Icons.Filled.Info
    }
    Snackbar(
        modifier = Modifier.padding(AssistoTokens.Spacing.MD),
        containerColor = AssistoTokens.Colors.BgSurface,
        shape = RoundedCornerShape(AssistoTokens.Radius.medium),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .size(width = 4.dp, height = 24.dp)
                    .background(accent, RoundedCornerShape(AssistoTokens.Radius.full)),
            )
            Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.padding(start = 12.dp).size(20.dp))
            Text(
                data.visuals.message,
                color = AssistoTokens.Colors.TextPrimary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
    }
}
