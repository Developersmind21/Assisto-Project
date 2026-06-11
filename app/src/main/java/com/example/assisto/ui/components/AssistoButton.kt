package com.example.assisto.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.theme.AccentRed
import com.example.assisto.ui.theme.PrimaryBlue
import com.example.assisto.ui.theme.SecondaryBlue

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
) {
    val baseModifier = modifier
        .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
        .height(48.dp)

    when (variant) {
        AssistoButtonVariant.Primary -> Button(
            onClick = onClick,
            enabled = enabled && !loading,
            modifier = baseModifier,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = Color.White,
            ),
        ) {
            ButtonContent(text, loading)
        }
        AssistoButtonVariant.Secondary -> Button(
            onClick = onClick,
            enabled = enabled && !loading,
            modifier = baseModifier,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = SecondaryBlue,
                contentColor = Color.White,
            ),
        ) {
            ButtonContent(text, loading)
        }
        AssistoButtonVariant.Ghost -> OutlinedButton(
            onClick = onClick,
            enabled = enabled && !loading,
            modifier = baseModifier,
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            ButtonContent(text, loading)
        }
        AssistoButtonVariant.Danger -> Button(
            onClick = onClick,
            enabled = enabled && !loading,
            modifier = baseModifier,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentRed,
                contentColor = Color.White,
            ),
        ) {
            ButtonContent(text, loading)
        }
    }
}

@Composable
private fun ButtonContent(text: String, loading: Boolean) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.dp,
            color = Color.White,
        )
    } else {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
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
