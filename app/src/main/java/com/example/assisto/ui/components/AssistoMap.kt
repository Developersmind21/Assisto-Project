package com.example.assisto.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.PrimaryBlue
import com.example.assisto.ui.theme.SecondaryBlue

@Composable
fun AssistoMapPlaceholder(
    modifier: Modifier = Modifier,
    address: String = "",
    showPin: Boolean = true,
    pinBounce: Boolean = false,
    overlayText: String? = null,
) {
    val scale by animateFloatAsState(
        targetValue = if (pinBounce) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "pinBounce",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(SecondaryBlue.copy(alpha = 0.15f), PrimaryBlue.copy(alpha = 0.08f)),
                ),
            ),
    ) {
        // Grid lines to simulate map
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
        )
        if (showPin) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = "Location pin",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(scale)
                    .size(48.dp),
            )
        }
        if (address.isNotEmpty()) {
            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                    .offset(y = (-16).dp),
            )
        }
        overlayText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.TopCenter).offset(y = 16.dp),
            )
        }
    }
}
