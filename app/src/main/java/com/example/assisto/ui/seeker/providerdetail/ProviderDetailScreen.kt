package com.example.assisto.ui.seeker.providerdetail

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.Review
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoBadge
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.BadgeType
import com.example.assisto.ui.components.LoadingShimmerList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProviderDetailScreen(
    onBack: () -> Unit,
    onRequest: (String) -> Unit,
    viewModel: ProviderDetailViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tabs = listOf("About", "Reviews", "Gallery")

    Scaffold(
        topBar = { AssistoTopBar("Provider Profile", onBackClick = onBack) },
        bottomBar = {
            when (val p = state.provider) {
                is UiState.Success -> AssistoButton(
                    text = "Request this Provider",
                    onClick = { onRequest(p.data.id) },
                    modifier = Modifier.padding(20.dp),
                )
                else -> {}
            }
        },
    ) { padding ->
        when (val provider = state.provider) {
            is UiState.Loading -> LoadingShimmerList(2)
            is UiState.Error -> Text(provider.message, Modifier.padding(20.dp))
            is UiState.Success -> {
                val p = provider.data
                Column(Modifier.fillMaxSize().padding(padding)) {
                    HeroSection(p)
                    TabRow(selectedTabIndex = state.selectedTab) {
                        tabs.forEachIndexed { i, t ->
                            Tab(selected = state.selectedTab == i, onClick = { viewModel.selectTab(i) }, text = { Text(t) })
                        }
                    }
                    when (state.selectedTab) {
                        0 -> AboutTab(p)
                        1 -> ReviewsTab(state.reviews)
                        2 -> GalleryTab(p)
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroSection(p: Provider) {
    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(p.avatarUrl, null, Modifier.size(96.dp).clip(RoundedCornerShape(48.dp)), contentScale = ContentScale.Crop)
        Text(p.name, style = MaterialTheme.typography.headlineMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistoBadge(BadgeType.Rating, "${p.rating} (${p.reviewCount} reviews)")
            if (p.verified) AssistoBadge(BadgeType.Verified, "Verified")
        }
        Text("${p.jobsCompleted} jobs completed", style = MaterialTheme.typography.bodyMedium)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AboutTab(p: Provider) {
    Column(Modifier.padding(20.dp)) {
        Text(p.bio, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(12.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            p.skills.forEach { AssistoBadge(BadgeType.Category, it) }
        }
        Spacer(Modifier.height(12.dp))
        Text("Response time: ~${p.responseTimeMinutes} min", style = MaterialTheme.typography.bodyMedium)
        Text("Member since ${p.memberSince}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun ReviewsTab(reviews: UiState<List<Review>>) {
    when (reviews) {
        is UiState.Success -> LazyColumn(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(reviews.data) { r ->
                AssistoCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(r.reviewerAvatarUrl, null, Modifier.size(36.dp).clip(RoundedCornerShape(18.dp)))
                        Column(Modifier.padding(start = 8.dp)) {
                            Text(r.reviewerName, style = MaterialTheme.typography.titleMedium)
                            Text(r.date, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Text("★ ${r.rating}", style = MaterialTheme.typography.labelLarge)
                    Text(r.comment, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        else -> LoadingShimmerList(2)
    }
}

@Composable
private fun GalleryTab(p: Provider) {
    if (p.galleryUrls.isEmpty()) {
        Text("No gallery photos yet.", Modifier.padding(20.dp))
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(p.galleryUrls) { url ->
                AsyncImage(url, null, Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
            }
        }
    }
}
