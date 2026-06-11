package com.example.assisto.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AccentRed
import com.example.assisto.ui.theme.PrimaryBlue
import com.example.assisto.ui.theme.SecondaryBlue

private val ButtonHeight = 56.dp
private val ButtonRadius = 12.dp

/** Shared content: leading icon + label, or a centered spinner when loading. */
@Composable
private fun ButtonInner(text: String, loading: Boolean, leadingIcon: ImageVector?, spinnerColor: Color) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.dp,
            color = spinnerColor,
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun pressScale(interactionSource: MutableInteractionSource): Float {
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonPressScale",
    )
    return scale
}

@Composable
fun AssistoPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    fillMaxWidth: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        interactionSource = interaction,
        modifier = modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .height(ButtonHeight)
            .scale(pressScale(interaction)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(ButtonRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = Color.White,
            disabledContainerColor = PrimaryBlue.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.8f),
        ),
    ) {
        ButtonInner(text, loading, leadingIcon, Color.White)
    }
}

@Composable
fun AssistoSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    fillMaxWidth: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    OutlinedButton(
        onClick = onClick,
        enabled = enabled && !loading,
        interactionSource = interaction,
        modifier = modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .height(ButtonHeight)
            .scale(pressScale(interaction)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(ButtonRadius),
        border = BorderStroke(1.5.dp, if (enabled) PrimaryBlue else PrimaryBlue.copy(alpha = 0.4f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
    ) {
        ButtonInner(text, loading, leadingIcon, PrimaryBlue)
    }
}

@Composable
fun AssistoGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    fillMaxWidth: Boolean = true,
    leadingIcon: ImageVector? = null,
    contentColor: Color = PrimaryBlue,
) {
    val interaction = remember { MutableInteractionSource() }
    TextButton(
        onClick = onClick,
        enabled = enabled && !loading,
        interactionSource = interaction,
        modifier = modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .height(ButtonHeight)
            .scale(pressScale(interaction)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(ButtonRadius),
        colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
    ) {
        ButtonInner(text, loading, leadingIcon, contentColor)
    }
}

@Composable
fun AssistoDangerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    fillMaxWidth: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        interactionSource = interaction,
        modifier = modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
            .height(ButtonHeight)
            .scale(pressScale(interaction)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(ButtonRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentRed,
            contentColor = Color.White,
            disabledContainerColor = AccentRed.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.8f),
        ),
    ) {
        ButtonInner(text, loading, leadingIcon, Color.White)
    }
}

// ── Back-compat shim ────────────────────────────────────────────────────
enum class AssistoButtonVariant { Primary, Secondary, Ghost, Danger }

@Composable
fun AssistoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AssistoButtonVariant = AssistoButtonVariant.Primary,
    enabled: Boolean = true,
    loading: Boolean = false,
    fillMaxWidth: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    when (variant) {
        AssistoButtonVariant.Primary -> AssistoPrimaryButton(text, onClick, modifier, enabled, loading, fillMaxWidth, leadingIcon)
        AssistoButtonVariant.Secondary -> AssistoSecondaryButton(text, onClick, modifier, enabled, loading, fillMaxWidth, leadingIcon)
        AssistoButtonVariant.Ghost -> AssistoGhostButton(text, onClick, modifier, enabled, loading, fillMaxWidth, leadingIcon)
        AssistoButtonVariant.Danger -> AssistoDangerButton(text, onClick, modifier, enabled, loading, fillMaxWidth, leadingIcon)
    }
}

@Composable
fun AssistoTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium, color = SecondaryBlue)
    }
}
