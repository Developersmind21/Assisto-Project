package com.example.assisto.ui.seeker.tracking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoButtonVariant
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoMapPlaceholder
import com.example.assisto.ui.components.SosFloatingButton
import com.example.assisto.ui.theme.SecondaryBlue

@Composable
fun RequestTrackingScreen(
    onBack: () -> Unit,
    onChat: (String) -> Unit,
    onSos: () -> Unit,
    onComplete: (String) -> Unit,
    viewModel: RequestTrackingViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        AssistoMapPlaceholder(pinBounce = state.revealedSteps >= 2)
        SosFloatingButton(onConfirm = onSos, modifier = Modifier.align(Alignment.TopEnd).padding(16.dp))
        AssistoCard(
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp).fillMaxWidth(),
            elevation = 8,
        ) {
            state.provider?.let { p ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(p.avatarUrl, null, Modifier.size(48.dp).clip(RoundedCornerShape(24.dp)), contentScale = ContentScale.Crop)
                    Column(Modifier.padding(start = 12.dp)) {
                        Text(p.name, style = MaterialTheme.typography.titleMedium)
                        Text("★ ${p.rating}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Arriving in ~${state.etaMinutes} min", style = MaterialTheme.typography.labelLarge, color = SecondaryBlue)
            Spacer(Modifier.height(12.dp))
            viewModel.timelineSteps().forEachIndexed { index, (_, label) ->
                AnimatedVisibility(visible = index < state.revealedSteps, enter = fadeIn()) {
                    Text("● $label", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistoButton("Chat", { onChat("conv1") }, Modifier.weight(1f), variant = AssistoButtonVariant.Secondary)
                AssistoButton("Call", {}, Modifier.weight(1f), variant = AssistoButtonVariant.Ghost)
                AssistoButton("Cancel", { viewModel.showCancel(true) }, Modifier.weight(1f), variant = AssistoButtonVariant.Danger)
            }
            Spacer(Modifier.height(8.dp))
            AssistoButton("Complete & Pay", { onComplete(viewModel.requestId) })
        }
    }
    if (state.showCancelDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showCancel(false) },
            title = { Text("Cancel request?") },
            text = { Text("You may be charged a cancellation fee if the provider is already en route.") },
            confirmButton = { TextButton(onClick = { viewModel.cancelRequest(); viewModel.showCancel(false); onBack() }) { Text("Cancel Request") } },
            dismissButton = { TextButton(onClick = { viewModel.showCancel(false) }) { Text("Keep") } },
        )
    }
}
