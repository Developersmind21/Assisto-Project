package com.example.assisto.ui.seeker.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoBadge
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.BadgeType
import com.example.assisto.ui.components.LoadingShimmerList
import com.example.assisto.ui.components.StatusChip
import com.example.assisto.ui.theme.SuccessGreen
import com.example.assisto.ui.theme.WarningOrange
import com.example.assisto.ui.theme.AccentRed
import com.example.assisto.ui.theme.SecondaryBlue

private data class CategoryItem(
    val category: ServiceCategory,
    val icon: ImageVector,
    val color: Color,
)

private val categories = listOf(
    CategoryItem(ServiceCategory.HOME, Icons.Default.Build, Color(0xFF1565C0)),
    CategoryItem(ServiceCategory.AUTO, Icons.Default.DirectionsCar, Color(0xFF6A1B9A)),
    CategoryItem(ServiceCategory.TECH, Icons.Default.Computer, Color(0xFF00838F)),
    CategoryItem(ServiceCategory.EDUCATION, Icons.Default.School, Color(0xFFEF6C00)),
    CategoryItem(ServiceCategory.PERSONAL, Icons.Default.Person, Color(0xFF2E7D32)),
)

@Composable
fun SeekerHomeScreen(
    onCategoryClick: (ServiceCategory) -> Unit,
    onQuickRequest: () -> Unit,
    onProviderClick: (String) -> Unit,
    onRequestClick: (String) -> Unit,
    viewModel: SeekerHomeViewModel = assistoViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onQuickRequest,
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Quick Request", tint = Color.White)
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Hi ${state.user?.fullName?.split(" ")?.firstOrNull() ?: "there"} 👋",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(16.dp), tint = SecondaryBlue)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = state.user?.city ?: "Austin, TX",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(20.dp))
            Text("Categories", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(280.dp),
            ) {
                items(categories) { item ->
                    CategoryCard(item, onClick = { onCategoryClick(item.category) })
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Recent Requests", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            when (val requests = state.recentRequests) {
                is UiState.Loading -> LoadingShimmerList(2)
                is UiState.Error -> Text(requests.message, color = MaterialTheme.colorScheme.error)
                is UiState.Success -> LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(requests.data) { req ->
                        RecentRequestCard(req, onClick = { onRequestClick(req.id) })
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Popular Providers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            when (val providers = state.featuredProviders) {
                is UiState.Loading -> LoadingShimmerList(2)
                is UiState.Error -> Text(providers.message)
                is UiState.Success -> LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(providers.data) { provider ->
                        ProviderCarouselCard(provider, onClick = { onProviderClick(provider.id) })
                    }
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(12.dp))
        Text("What do you need help with today?", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun CategoryCard(item: CategoryItem, onClick: () -> Unit) {
    AssistoCard(onClick = onClick, elevation = 2) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(item.color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(item.icon, null, tint = item.color, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(item.category.displayName, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun RecentRequestCard(request: ServiceRequest, onClick: () -> Unit) {
    AssistoCard(
        modifier = Modifier.width(200.dp),
        onClick = onClick,
        elevation = 2,
    ) {
        StatusChip(request.status.name.replace('_', ' '), statusColor(request.status))
        Spacer(modifier = Modifier.height(8.dp))
        Text(request.subCategory, style = MaterialTheme.typography.titleMedium)
        Text(request.createdAt, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProviderCarouselCard(provider: Provider, onClick: () -> Unit) {
    AssistoCard(modifier = Modifier.width(180.dp), onClick = onClick, elevation = 2) {
        AsyncImage(
            model = provider.avatarUrl,
            contentDescription = provider.name,
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(provider.name, style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            AssistoBadge(BadgeType.Rating, "${provider.rating}")
            if (provider.verified) AssistoBadge(BadgeType.Verified, "Verified")
        }
        Text(provider.topSkill, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("${provider.distanceMiles} mi away", style = MaterialTheme.typography.labelSmall, color = SecondaryBlue)
    }
}

private fun statusColor(status: RequestStatus) = when (status) {
    RequestStatus.PENDING -> WarningOrange
    RequestStatus.ACCEPTED, RequestStatus.EN_ROUTE -> SecondaryBlue
    RequestStatus.IN_PROGRESS -> WarningOrange
    RequestStatus.COMPLETED -> SuccessGreen
    RequestStatus.CANCELLED -> AccentRed
    else -> SecondaryBlue
}
