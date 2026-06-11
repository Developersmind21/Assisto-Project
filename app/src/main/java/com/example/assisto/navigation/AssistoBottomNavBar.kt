package com.example.assisto.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AssistoTokens

data class BottomNavEntry(val route: String, val label: String, val icon: ImageVector)

/**
 * Spec bottom navigation: white bg, 1dp Gray200 top border, no elevation,
 * PrimaryBlue active / Gray500 inactive.
 */
@Composable
fun AssistoBottomNavBar(
    items: List<BottomNavEntry>,
    currentRoute: String?,
    onSelect: (String) -> Unit,
) {
    Column {
        HorizontalDivider(thickness = 1.dp, color = AssistoTokens.Colors.Gray200)
        NavigationBar(
            containerColor = AssistoTokens.Colors.BgSurface,
            tonalElevation = 0.dp,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = { onSelect(item.route) },
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AssistoTokens.Colors.PrimaryBlue,
                        selectedTextColor = AssistoTokens.Colors.PrimaryBlue,
                        unselectedIconColor = AssistoTokens.Colors.Gray500,
                        unselectedTextColor = AssistoTokens.Colors.Gray500,
                        indicatorColor = AssistoTokens.Colors.LightBlue,
                    ),
                )
            }
        }
    }
}
