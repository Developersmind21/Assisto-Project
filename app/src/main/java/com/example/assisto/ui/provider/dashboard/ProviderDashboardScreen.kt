package com.example.assisto.ui.provider.dashboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.assisto.data.model.IncomingJobRequest
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.TopBarActionIcon
import com.example.assisto.ui.provider.incoming.IncomingRequestSheet
import com.example.assisto.ui.theme.SuccessGreen
import com.example.assisto.util.NotificationHelper
import androidx.compose.ui.platform.LocalContext

@Composable
fun ProviderDashboardScreen(
    onEarningsClick: () -> Unit,
    onActiveJobClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    viewModel: ProviderDashboardViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val pulse = rememberInfiniteTransition(label = "pulse").animateFloat(
        1f, 1.03f, infiniteRepeatable(tween(600), RepeatMode.Reverse), label = "scale",
    )
    val availColor by animateColorAsState(if (state.isAvailable) SuccessGreen else Color.Gray, label = "avail")
    val switchScale by androidx.compose.animation.core.animateFloatAsState(
        if (state.isAvailable) 1.05f else 1f, spring(), label = "sw",
    )

    LaunchedEffect(state.isAvailable, state.incomingRequests.size) {
        if (state.isAvailable && state.incomingRequests.isNotEmpty()) {
            NotificationHelper.showIncomingRequestNotification(context)
        }
    }

    val showIncoming = state.isAvailable && state.incomingRequests.isNotEmpty()

    Scaffold(
        topBar = {
            AssistoTopBar("Assisto Provider", actions = {
                TopBarActionIcon(Icons.Default.AttachMoney, "Earnings", onEarningsClick)
            })
        },
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp).verticalScroll(rememberScrollState()),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = state.user?.avatarUrl,
                    contentDescription = "Profile",
                    modifier = Modifier.size(40.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(16.dp))
            AssistoCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(if (state.isAvailable) "You are ACTIVE" else "You are OFFLINE", style = MaterialTheme.typography.titleLarge, color = availColor)
                        Text("Availability", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = state.isAvailable,
                        onCheckedChange = { viewModel.toggleAvailability() },
                        modifier = Modifier.scale(switchScale),
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SummaryCard("Jobs Today", "${state.jobsToday}", Modifier.weight(1f))
                SummaryCard("Earnings", "$${state.earningsToday.toInt()}", Modifier.weight(1f))
                SummaryCard("Rating", "${state.avgRating}", Modifier.weight(1f))
            }
            state.activeJob?.let { job ->
                Spacer(Modifier.height(16.dp))
                AssistoCard(
                    modifier = Modifier.scale(pulse.value).border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large),
                    onClick = { onActiveJobClick(job.id) },
                ) {
                    Text("Active Job", style = MaterialTheme.typography.titleMedium)
                    Text("${job.subCategory} · ${job.seekerName}")
                    Text("ETA: ${job.etaMinutes} min")
                    AssistoButton("View Details", { onActiveJobClick(job.id) }, fillMaxWidth = true)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("This Week", style = MaterialTheme.typography.titleMedium)
            WeeklyChart(state.weeklyChart)
            Spacer(Modifier.height(80.dp))
        }
    }

    if (showIncoming) {
        IncomingRequestSheet(
            request = state.incomingRequests.first(),
            onAccept = { viewModel.acceptRequest(it) },
            onDecline = { viewModel.declineRequest(it) },
            onDismiss = { viewModel.declineRequest(state.incomingRequests.first().id) },
        )
    }
}

@Composable
private fun SummaryCard(label: String, value: String, modifier: Modifier = Modifier) {
    AssistoCard(modifier = modifier, elevation = 2) {
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
private fun WeeklyChart(values: List<Float>) {
    val max = values.maxOrNull() ?: 1f
    Canvas(Modifier.fillMaxWidth().height(120.dp).padding(top = 8.dp)) {
        val barWidth = size.width / (values.size * 2)
        values.forEachIndexed { i, v ->
            val h = (v / max) * size.height
            drawRoundRect(
                color = Color(0xFF1B4F8A),
                topLeft = Offset(i * barWidth * 2 + barWidth / 2, size.height - h),
                size = Size(barWidth, h),
                cornerRadius = CornerRadius(8f, 8f),
            )
        }
    }
}
