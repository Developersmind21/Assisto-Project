package com.example.assisto.ui.seeker.matching

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.SortOption
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoBadge
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoButtonVariant
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoMapPlaceholder
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.BadgeType
import com.example.assisto.ui.components.EmptyStateView
import com.example.assisto.ui.components.LoadingShimmerList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProviderMatchingScreen(
    onBack: () -> Unit,
    onViewProfile: (String) -> Unit,
    onRequestProvider: (String) -> Unit,
    viewModel: ProviderMatchingViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pulse = rememberInfiniteTransition(label = "pulse").animateFloat(
        0.4f, 1f, infiniteRepeatable(tween(800), RepeatMode.Reverse), label = "alpha",
    )

    Scaffold(topBar = { AssistoTopBar("Find Providers", onBackClick = onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Column(Modifier.padding(20.dp)) {
                Text("Finding providers near you…", style = MaterialTheme.typography.titleLarge)
                Box(Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(16.dp)).alpha(pulse.value)) {
                    AssistoMapPlaceholder(overlayText = "Searching…")
                }
            }
            FlowRow(
                Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SortOption.entries.forEach { sort ->
                    FilterChip(selected = state.sortBy == sort, onClick = { viewModel.setSort(sort) }, label = { Text(sort.label) })
                }
            }
            Spacer(Modifier.height(12.dp))
            when (val providers = state.providers) {
                is UiState.Loading -> LoadingShimmerList(3)
                is UiState.Error -> com.example.assisto.ui.components.ErrorStateView(providers.message, onRetry = viewModel::retry)
                is UiState.Success -> if (providers.data.isEmpty()) {
                    EmptyStateView("No providers nearby", "Try expanding your search radius.", "Expand search radius", viewModel::expandRadius)
                } else {
                    LazyColumn(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(providers.data) { p ->
                            MatchingProviderCard(p, onViewProfile, onRequestProvider)
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchingProviderCard(provider: Provider, onViewProfile: (String) -> Unit, onRequest: (String) -> Unit) {
    AssistoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(provider.avatarUrl, null, Modifier.size(56.dp).clip(RoundedCornerShape(28.dp)), contentScale = ContentScale.Crop)
            Column(Modifier.padding(start = 12.dp).weight(1f)) {
                Text(provider.name, style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AssistoBadge(BadgeType.Rating, "${provider.rating} (${provider.reviewCount})")
                    if (provider.verified) AssistoBadge(BadgeType.Verified, "Verified")
                }
                Text(provider.skills.take(2).joinToString(" · "), style = MaterialTheme.typography.bodySmall)
                Text("${provider.distanceMiles} mi · ~${provider.etaMinutes} min · ${provider.priceRange}", style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistoButton("View Profile", { onViewProfile(provider.id) }, Modifier.weight(1f), variant = AssistoButtonVariant.Ghost)
            AssistoButton("Request", { onRequest(provider.id) }, Modifier.weight(1f))
        }
    }
}
