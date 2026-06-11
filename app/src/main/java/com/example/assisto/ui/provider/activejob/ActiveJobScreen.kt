package com.example.assisto.ui.provider.activejob

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoButtonVariant
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.SosFloatingButton

@Composable
fun ActiveJobScreen(onBack: () -> Unit, onSos: () -> Unit, onChat: () -> Unit, viewModel: ActiveJobViewModel = assistoViewModel()) {
    val job by viewModel.job.collectAsStateWithLifecycle()

    Scaffold(topBar = { AssistoTopBar("Active Job", onBackClick = onBack) }) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            Column(Modifier.padding(20.dp)) {
                job?.let { j ->
                    AssistoCard {
                        Text("Navigate to", style = MaterialTheme.typography.titleMedium)
                        Text(j.address, style = MaterialTheme.typography.bodyLarge)
                        Text("${j.distanceMiles} mi · ETA ${j.etaMinutes} min")
                        AssistoButton("Open in Maps", {}, variant = AssistoButtonVariant.Secondary)
                    }
                    Spacer(Modifier.height(16.dp))
                    AssistoCard {
                        RowWithAvatar(j.seekerName, j.seekerAvatarUrl, j.seekerPhoneMasked)
                        AssistoButton("Chat with Seeker", onChat, variant = AssistoButtonVariant.Ghost)
                    }
                    Spacer(Modifier.height(16.dp))
                    when (j.status) {
                        RequestStatus.ACCEPTED, RequestStatus.EN_ROUTE -> AssistoButton("I've Arrived", { viewModel.updateStatus(RequestStatus.ARRIVED) })
                        RequestStatus.ARRIVED -> AssistoButton("Job Started", { viewModel.updateStatus(RequestStatus.IN_PROGRESS) })
                        RequestStatus.IN_PROGRESS -> AssistoButton("Mark Complete", { viewModel.updateStatus(RequestStatus.COMPLETED) })
                        else -> Text("Status: ${j.status}")
                    }
                }
            }
            SosFloatingButton(onConfirm = onSos, modifier = Modifier.align(Alignment.TopEnd).padding(16.dp))
        }
    }
}

@Composable
private fun RowWithAvatar(name: String, avatar: String, phone: String) {
    androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(avatar, null, Modifier.size(48.dp).clip(RoundedCornerShape(24.dp)), contentScale = ContentScale.Crop)
        Column(Modifier.padding(start = 12.dp)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text(phone, style = MaterialTheme.typography.bodySmall)
        }
    }
}
