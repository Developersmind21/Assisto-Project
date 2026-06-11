package com.example.assisto.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Central design-token source for Assisto. Every screen and component must pull
 * colors, spacing, shapes and dimensions from here — no magic numbers in screens.
 */
object AssistoTokens {

    // ── Colors ─────────────────────────────────────────────────────────
    object Colors {
        val PrimaryBlue = Color(0xFF1B4F8A) // main brand
        val SecondaryBlue = Color(0xFF2E75B6) // interactive elements
        val LightBlue = Color(0xFFD6E4F7) // backgrounds, chips
        val AccentRed = Color(0xFFE8412B) // SOS, danger, alerts

        val BgPage = Color(0xFFF2F4F7) // page background
        val BgSurface = Color(0xFFFFFFFF) // cards, sheets
        val BgElevated = Color(0xFFF8FAFC) // elevated surfaces

        val TextPrimary = Color(0xFF1A1A1A)
        val TextSecondary = Color(0xFF6B7280)
        val TextDisabled = Color(0xFFADB5BD)

        val SuccessGreen = Color(0xFF2E7D32)
        val SuccessBg = Color(0xFFE8F5E9)
        val WarningOrange = Color(0xFFF57C00)
        val WarningBg = Color(0xFFFFF3E0)
        val ErrorRed = Color(0xFFD32F2F)
        val ErrorBg = Color(0xFFFFEBEE)

        val Gray100 = Color(0xFFF8F9FA)
        val Gray200 = Color(0xFFE9ECEF)
        val Gray300 = Color(0xFFDEE2E6)
        val Gray400 = Color(0xFFCED4DA)
        val Gray500 = Color(0xFFADB5BD)

        // Category accents (Phase 2 palette)
        val CategoryHome = Color(0xFFFF6B35) // orange
        val CategoryAuto = Color(0xFF2E75B6) // blue
        val CategoryTech = Color(0xFF7B2FBE) // purple
        val CategoryEducation = Color(0xFF2E7D32) // green
        val CategoryPersonal = Color(0xFFC2185B) // pink

        // Dark-mode variants
        val DarkBgPage = Color(0xFF121212)
        val DarkBgSurface = Color(0xFF1E1E1E)
        val DarkBgElevated = Color(0xFF242424)
        val DarkTextPrimary = Color(0xFFF5F5F5)
        val DarkTextSecondary = Color(0xFFB0B0B0)
        val DarkTextDisabled = Color(0xFF6B7280)
        val DarkGray200 = Color(0xFF2A2A2A)
        val DarkGray300 = Color(0xFF333333)
    }

    // ── Spacing ────────────────────────────────────────────────────────
    object Spacing {
        val XS = 4.dp
        val SM = 8.dp
        val MD = 16.dp
        val LG = 24.dp
        val XL = 32.dp
        val XXL = 48.dp
    }

    // ── Shape radii ────────────────────────────────────────────────────
    object Radius {
        val extraSmall = 4.dp
        val small = 8.dp
        val medium = 12.dp
        val large = 16.dp
        val extraLarge = 24.dp
        val full = 50.dp
    }

    // ── Dimensions ─────────────────────────────────────────────────────
    object Dimens {
        val ButtonHeight = 56.dp
        val InputHeight = 56.dp
        val CardRadius = 16.dp
        val AvatarSM = 40.dp
        val AvatarMD = 56.dp
        val AvatarLG = 80.dp
        val BottomNavHeight = 80.dp
        val TopBarHeight = 64.dp
    }
}
