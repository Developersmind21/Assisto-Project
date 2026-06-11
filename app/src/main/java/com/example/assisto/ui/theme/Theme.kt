package com.example.assisto.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = SurfaceWhite,
    primaryContainer = SecondaryBlue,
    onPrimaryContainer = SurfaceWhite,
    secondary = SecondaryBlue,
    onSecondary = SurfaceWhite,
    tertiary = AccentRed,
    onTertiary = SurfaceWhite,
    background = BgPage,
    onBackground = TextPrimary,
    surface = BgSurface,
    onSurface = TextPrimary,
    surfaceVariant = Gray200,
    onSurfaceVariant = TextSecondary,
    outline = Gray300,
    outlineVariant = Gray200,
    error = ErrorRed,
    onError = SurfaceWhite,
)

private val DarkColorScheme = darkColorScheme(
    primary = SecondaryBlue,
    onPrimary = DarkTextPrimary,
    primaryContainer = PrimaryBlue,
    onPrimaryContainer = DarkTextPrimary,
    secondary = SecondaryBlue,
    onSecondary = DarkTextPrimary,
    tertiary = AccentRed,
    onTertiary = DarkTextPrimary,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkTextSecondary,
    outlineVariant = DarkElevated,
    error = ErrorRed,
    onError = DarkTextPrimary,
)

val AssistoShapes = Shapes(
    extraSmall = RoundedCornerShape(AssistoTokens.Radius.extraSmall),
    small = RoundedCornerShape(AssistoTokens.Radius.small),
    medium = RoundedCornerShape(AssistoTokens.Radius.medium),
    large = RoundedCornerShape(AssistoTokens.Radius.large),
    extraLarge = RoundedCornerShape(AssistoTokens.Radius.extraLarge),
)

@Composable
fun AssistoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AssistoShapes,
        content = content,
    )
}
