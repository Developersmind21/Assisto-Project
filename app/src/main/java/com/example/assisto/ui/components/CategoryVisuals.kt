package com.example.assisto.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.ui.theme.AssistoTokens

/** Icon + accent color descriptor for a [ServiceCategory]. */
data class CategoryVisual(val icon: ImageVector, val color: Color)

fun ServiceCategory.visual(): CategoryVisual = when (this) {
    ServiceCategory.HOME -> CategoryVisual(Icons.Filled.Home, AssistoTokens.Colors.CategoryHome)
    ServiceCategory.AUTO -> CategoryVisual(Icons.Filled.DirectionsCar, AssistoTokens.Colors.CategoryAuto)
    ServiceCategory.TECH -> CategoryVisual(Icons.Filled.Build, AssistoTokens.Colors.CategoryTech)
    ServiceCategory.EDUCATION -> CategoryVisual(Icons.Filled.MenuBook, AssistoTokens.Colors.CategoryEducation)
    ServiceCategory.PERSONAL -> CategoryVisual(Icons.Filled.SupportAgent, AssistoTokens.Colors.CategoryPersonal)
}

fun ServiceCategory.accentColor(): Color = visual().color

fun ServiceCategory.icon(): ImageVector = visual().icon
