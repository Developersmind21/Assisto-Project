package com.example.assisto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.ui.theme.AssistoTokens

/** Visual descriptor for each request status. */
private data class StatusStyle(
    val label: String,
    val bg: Color,
    val fg: Color,
    val icon: ImageVector,
)

@Composable
private fun styleFor(status: RequestStatus): StatusStyle = when (status) {
    RequestStatus.PENDING -> StatusStyle("Pending", AssistoTokens.Colors.WarningBg, AssistoTokens.Colors.WarningOrange, Icons.Filled.HourglassEmpty)
    RequestStatus.ACCEPTED -> StatusStyle("Accepted", AssistoTokens.Colors.LightBlue, AssistoTokens.Colors.PrimaryBlue, Icons.Filled.CheckCircle)
    RequestStatus.EN_ROUTE -> StatusStyle("En Route", AssistoTokens.Colors.LightBlue, AssistoTokens.Colors.SecondaryBlue, Icons.Filled.DirectionsCar)
    RequestStatus.ARRIVED -> StatusStyle("Arrived", AssistoTokens.Colors.SuccessBg, AssistoTokens.Colors.SuccessGreen, Icons.Filled.CheckCircle)
    RequestStatus.IN_PROGRESS -> StatusStyle("In Progress", AssistoTokens.Colors.WarningBg, AssistoTokens.Colors.WarningOrange, Icons.Filled.PlayCircle)
    RequestStatus.COMPLETED -> StatusStyle("Completed", AssistoTokens.Colors.SuccessBg, AssistoTokens.Colors.SuccessGreen, Icons.Filled.ThumbUp)
    RequestStatus.CANCELLED -> StatusStyle("Cancelled", AssistoTokens.Colors.ErrorBg, AssistoTokens.Colors.ErrorRed, Icons.Filled.Cancel)
}

/**
 * Spec status pill: full-radius capsule, 4dp×12dp padding, status-specific bg/fg/icon.
 */
@Composable
fun StatusChip(
    status: RequestStatus,
    modifier: Modifier = Modifier,
) {
    val s = styleFor(status)
    Row(
        modifier = modifier
            .background(s.bg, RoundedCornerShape(AssistoTokens.Radius.full))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(s.icon, contentDescription = null, tint = s.fg, modifier = Modifier.size(14.dp))
        Text(
            text = s.label,
            style = MaterialTheme.typography.labelMedium,
            color = s.fg,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}
