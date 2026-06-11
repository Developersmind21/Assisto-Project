package com.example.assisto.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.SecondaryBlue
import com.example.assisto.ui.theme.SuccessGreen
import com.example.assisto.ui.theme.WarningOrange

enum class BadgeType { Verified, Rating, Category }

@Composable
fun AssistoBadge(
    type: BadgeType,
    text: String,
    modifier: Modifier = Modifier,
) {
    val (bg, fg, icon) = when (type) {
        BadgeType.Verified -> Triple(SuccessGreen.copy(alpha = 0.15f), SuccessGreen, Icons.Default.CheckCircle)
        BadgeType.Rating -> Triple(WarningOrange.copy(alpha = 0.15f), WarningOrange, Icons.Default.Star)
        BadgeType.Category -> Triple(SecondaryBlue.copy(alpha = 0.15f), SecondaryBlue, null)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Icon(it, contentDescription = null, tint = fg, modifier = Modifier.size(14.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = fg,
            modifier = Modifier.padding(start = if (icon != null) 4.dp else 0.dp),
        )
    }
}

@Composable
fun StatusChip(
    status: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = status,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}
