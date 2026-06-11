package com.example.assisto.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AssistoTokens

/**
 * Surface card: 16dp radius, 2dp elevation. [isSelected] adds a 2dp PrimaryBlue border.
 */
@Composable
fun AssistoCard(
    modifier: Modifier = Modifier,
    containerColor: Color = AssistoTokens.Colors.BgSurface,
    elevation: Int = 2,
    onClick: (() -> Unit)? = null,
    isSelected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(AssistoTokens.Spacing.MD),
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(AssistoTokens.Dimens.CardRadius)
    val border = if (isSelected) BorderStroke(2.dp, AssistoTokens.Colors.PrimaryBlue) else null
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = containerColor),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation.dp),
            border = border,
        ) {
            Column(modifier = Modifier.padding(contentPadding), content = content)
        }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = containerColor),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation.dp),
            border = border,
        ) {
            Column(modifier = Modifier.padding(contentPadding), content = content)
        }
    }
}
