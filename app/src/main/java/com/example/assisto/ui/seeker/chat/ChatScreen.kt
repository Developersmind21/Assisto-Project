package com.example.assisto.ui.seeker.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assisto.data.model.ChatMessage
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.TopBarActionIcon

@Composable
fun ChatScreen(onBack: () -> Unit, viewModel: ChatViewModel = assistoViewModel()) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val input by viewModel.input.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AssistoTopBar("Marcus Rivera", onBackClick = onBack, actions = {
                TopBarActionIcon(Icons.Default.Call, "Call") {}
            })
        },
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(
                Modifier.weight(1f).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = false,
            ) {
                items(messages) { msg -> ChatBubble(msg) }
            }
            Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = viewModel::updateInput,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message…") },
                )
                IconButton(onClick = viewModel::send) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(msg: ChatMessage) {
    if (msg.isSystem) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(msg.content, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(8.dp))
        }
    } else {
        val align = if (msg.isFromSeeker) Alignment.CenterEnd else Alignment.CenterStart
        val color = if (msg.isFromSeeker) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        val textColor = if (msg.isFromSeeker) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        Box(Modifier.fillMaxWidth(), contentAlignment = align) {
            Column(
                Modifier.widthIn(max = 280.dp).clip(RoundedCornerShape(16.dp)).background(color).padding(12.dp),
            ) {
                Text(msg.content, color = textColor)
                Text(msg.timestamp, style = MaterialTheme.typography.labelSmall, color = textColor.copy(alpha = 0.7f))
            }
        }
    }
}
