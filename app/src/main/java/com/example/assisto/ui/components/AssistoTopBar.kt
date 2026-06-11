package com.example.assisto.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AssistoTokens

/** A single top-bar action (icon button). Max 2 are rendered. */
data class TopBarAction(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistoTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: List<TopBarAction> = emptyList(),
    isTransparent: Boolean = false,
) {
    if (onBackClick != null) {
        BackHandler { onBackClick() }
    }
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AssistoTokens.Colors.TextPrimary,
                )
            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            },
            actions = {
                actions.take(2).forEach { action ->
                    IconButton(onClick = action.onClick) {
                        Icon(action.icon, contentDescription = action.contentDescription)
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = if (isTransparent) Color.Transparent else AssistoTokens.Colors.BgSurface,
                titleContentColor = AssistoTokens.Colors.TextPrimary,
            ),
            modifier = modifier,
        )
        if (!isTransparent) {
            HorizontalDivider(thickness = 1.dp, color = AssistoTokens.Colors.Gray200)
        }
    }
}

@Composable
fun TopBarActionIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(icon, contentDescription = contentDescription)
    }
}
