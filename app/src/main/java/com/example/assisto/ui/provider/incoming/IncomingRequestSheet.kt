package com.example.assisto.ui.provider.incoming

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.assisto.data.model.IncomingJobRequest
import com.example.assisto.ui.components.AssistoBottomSheet
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoButtonVariant
import com.example.assisto.ui.components.AssistoMapPlaceholder
import kotlinx.coroutines.delay

@Composable
fun IncomingRequestSheet(
    request: IncomingJobRequest,
    onAccept: (String) -> Unit,
    onDecline: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var secondsLeft by remember { mutableIntStateOf(90) }
    LaunchedEffect(request.id) {
        secondsLeft = 90
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
        onDismiss()
    }

    AssistoBottomSheet(visible = true, onDismiss = onDismiss) {
        Text("New Request!", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(64.dp)) {
                CircularProgressIndicator(
                    progress = { secondsLeft / 90f },
                    modifier = Modifier.size(64.dp),
                    strokeWidth = 4.dp,
                )
                Text("$secondsLeft", modifier = Modifier.align(Alignment.Center), style = MaterialTheme.typography.titleMedium)
            }
            Column(Modifier.padding(start = 16.dp)) {
                Text(request.subCategory, style = MaterialTheme.typography.titleMedium)
                Text(request.category.displayName, style = MaterialTheme.typography.bodySmall)
                Text(request.estimatedPayRange, style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(request.description, style = MaterialTheme.typography.bodyMedium)
        Text("${request.seekerDistanceMiles} mi away · ${request.seekerAddress}", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(12.dp))
        Box(Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(12.dp))) {
            AssistoMapPlaceholder()
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AssistoButton("Decline", { onDecline(request.id) }, Modifier.weight(1f), variant = AssistoButtonVariant.Ghost)
            AssistoButton("Accept", { onAccept(request.id) }, Modifier.weight(1f))
        }
    }
}
