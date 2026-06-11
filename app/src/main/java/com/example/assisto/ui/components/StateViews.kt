package com.example.assisto.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Inbox
import com.example.assisto.ui.theme.AssistoTokens

/**
 * Animated gradient skeleton sweeping Gray200 → Gray100 → Gray200 left-to-right.
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    height: Dp = 80.dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(AssistoTokens.Radius.large),
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val offset by transition.animateFloat(
        initialValue = -300f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Restart),
        label = "shimmerOffset",
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            AssistoTokens.Colors.Gray200,
            AssistoTokens.Colors.Gray100,
            AssistoTokens.Colors.Gray200,
        ),
        start = Offset(offset, 0f),
        end = Offset(offset + 300f, 0f),
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape)
            .background(brush),
    )
}

@Composable
private fun ShimmerBlock(width: Dp, height: Dp, shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(AssistoTokens.Radius.small)) {
    ShimmerBox(modifier = Modifier.width(width), height = height, shape = shape)
}

/** Provider list skeleton: avatar + two text lines + chips. */
@Composable
fun ProviderCardShimmer(modifier: Modifier = Modifier) {
    AssistoCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(AssistoTokens.Spacing.MD)) {
            ShimmerBox(modifier = Modifier.size(AssistoTokens.Dimens.AvatarMD), height = AssistoTokens.Dimens.AvatarMD, shape = CircleShape)
            Column(verticalArrangement = Arrangement.spacedBy(AssistoTokens.Spacing.SM)) {
                ShimmerBlock(width = 160.dp, height = 16.dp)
                ShimmerBlock(width = 100.dp, height = 12.dp)
                ShimmerBlock(width = 200.dp, height = 12.dp)
            }
        }
    }
}

/** Request list skeleton. */
@Composable
fun RequestCardShimmer(modifier: Modifier = Modifier) {
    AssistoCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(AssistoTokens.Spacing.MD)) {
            ShimmerBox(modifier = Modifier.size(AssistoTokens.Dimens.AvatarSM), height = AssistoTokens.Dimens.AvatarSM, shape = RoundedCornerShape(AssistoTokens.Radius.medium))
            Column(verticalArrangement = Arrangement.spacedBy(AssistoTokens.Spacing.SM)) {
                ShimmerBlock(width = 180.dp, height = 16.dp)
                ShimmerBlock(width = 120.dp, height = 12.dp)
            }
        }
        Spacer(Modifier.height(AssistoTokens.Spacing.SM))
        ShimmerBlock(width = 240.dp, height = 12.dp)
    }
}

@Composable
fun LoadingShimmerList(count: Int = 3) {
    Column(verticalArrangement = Arrangement.spacedBy(AssistoTokens.Spacing.MD)) {
        repeat(count) { RequestCardShimmer() }
    }
}

@Composable
fun EmptyStateView(
    title: String,
    message: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Inbox,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AssistoTokens.Spacing.XL),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = AssistoTokens.Colors.Gray400,
        )
        Spacer(modifier = Modifier.height(AssistoTokens.Spacing.MD))
        Text(title, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center, color = AssistoTokens.Colors.TextPrimary)
        Spacer(modifier = Modifier.height(AssistoTokens.Spacing.SM))
        Text(message, style = MaterialTheme.typography.bodyMedium, color = AssistoTokens.Colors.TextSecondary, textAlign = TextAlign.Center)
        if (actionLabel != null && onAction != null) {
            Spacer(modifier = Modifier.height(AssistoTokens.Spacing.LG))
            AssistoPrimaryButton(text = actionLabel, onClick = onAction, fillMaxWidth = false)
        }
    }
}

@Composable
fun ErrorStateView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AssistoTokens.Spacing.XL),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(Icons.Outlined.ErrorOutline, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(AssistoTokens.Spacing.SM))
        Text(message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = AssistoTokens.Colors.TextPrimary)
        Spacer(modifier = Modifier.height(AssistoTokens.Spacing.MD))
        AssistoSecondaryButton(text = "Retry", onClick = onRetry, fillMaxWidth = false)
    }
}
