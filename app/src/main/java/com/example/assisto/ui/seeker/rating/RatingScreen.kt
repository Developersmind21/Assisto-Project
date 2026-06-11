package com.example.assisto.ui.seeker.rating

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoTextField
import com.example.assisto.ui.components.AssistoTextLink
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.theme.WarningOrange

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingScreen(
    onBack: () -> Unit,
    onSubmitted: () -> Unit,
    viewModel: RatingViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = { AssistoTopBar("Rate Experience", onBackClick = onBack) }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("How was your experience?", style = MaterialTheme.typography.headlineMedium)
            Text("Marcus Rivera", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { star ->
                    val scale by animateFloatAsState(
                        if (star <= state.rating) 1.2f else 1f,
                        spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        label = "star$star",
                    )
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "$star stars",
                        tint = if (star <= state.rating) WarningOrange else Color.Gray,
                        modifier = Modifier.size(48.dp).scale(scale).clickable { viewModel.setRating(star) },
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                viewModel.availableTags.forEach { tag ->
                    FilterChip(
                        selected = tag in state.selectedTags,
                        onClick = { viewModel.toggleTag(tag) },
                        label = { Text(tag) },
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            AssistoTextField(state.review, viewModel::setReview, "Write a review (optional)", singleLine = false, minLines = 3)
            Spacer(Modifier.height(24.dp))
            AssistoButton("Submit Review", { viewModel.submit(onSubmitted) }, enabled = state.rating > 0, loading = state.isSubmitting)
            AssistoTextLink("Skip for now", { viewModel.skip(onSubmitted) })
        }
    }
}
