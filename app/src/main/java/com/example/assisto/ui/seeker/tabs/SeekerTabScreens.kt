package com.example.assisto.ui.seeker.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.assisto.data.model.Conversation
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.UserProfile
import com.example.assisto.data.repository.RequestRepository
import com.example.assisto.data.repository.UserRepository
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.LoadingShimmerList
import com.example.assisto.ui.components.StatusChip
import com.example.assisto.ui.theme.AccentRed
import com.example.assisto.ui.theme.SecondaryBlue
import com.example.assisto.ui.theme.SuccessGreen
import com.example.assisto.ui.theme.WarningOrange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SeekerRequestsViewModel constructor(private val repo: RequestRepository) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<ServiceRequest>>>(UiState.Loading)
    val state: StateFlow<UiState<List<ServiceRequest>>> = _state.asStateFlow()
    init { viewModelScope.launch { repo.getMyRequests().collect { _state.value = UiState.Success(it) } } }
}

@Composable
fun SeekerRequestsScreen(onRequestClick: (String) -> Unit, vm: SeekerRequestsViewModel = assistoViewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    Scaffold(topBar = { AssistoTopBar("My Requests") }) { padding ->
        when (val s = state) {
            is UiState.Loading -> LoadingShimmerList(3)
            is UiState.Success -> LazyColumn(Modifier.padding(padding).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(s.data) { req ->
                    AssistoCard(onClick = { onRequestClick(req.id) }) {
                        StatusChip(req.status.name.replace('_', ' '), statusColor(req.status))
                        Text(req.subCategory, style = MaterialTheme.typography.titleMedium)
                        Text(req.description.take(60), style = MaterialTheme.typography.bodySmall)
                        Text(req.createdAt, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            is UiState.Error -> Text(s.message, Modifier.padding(20.dp))
        }
    }
}

class SeekerMessagesViewModel constructor(private val repo: RequestRepository) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<Conversation>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Conversation>>> = _state.asStateFlow()
    init { viewModelScope.launch { repo.getConversations().collect { _state.value = UiState.Success(it) } } }
}

@Composable
fun SeekerMessagesScreen(onChatClick: (String) -> Unit, vm: SeekerMessagesViewModel = assistoViewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    Scaffold(topBar = { AssistoTopBar("Messages") }) { padding ->
        when (val s = state) {
            is UiState.Loading -> LoadingShimmerList(2)
            is UiState.Success -> LazyColumn(Modifier.padding(padding).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(s.data) { conv ->
                    AssistoCard(onClick = { onChatClick(conv.id) }) {
                        androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(conv.participantAvatarUrl, null, Modifier.size(48.dp).clip(RoundedCornerShape(24.dp)), contentScale = ContentScale.Crop)
                            Column(Modifier.padding(start = 12.dp)) {
                                Text(conv.participantName, style = MaterialTheme.typography.titleMedium)
                                Text(conv.lastMessage, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
            is UiState.Error -> Text(s.message)
        }
    }
}

class SeekerProfileTabViewModel constructor(private val repo: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<UserProfile?>(null)
    val user: StateFlow<UserProfile?> = _user.asStateFlow()
    init { viewModelScope.launch { repo.getCurrentUser().collect { _user.value = it } } }
}

@Composable
fun SeekerProfileScreen(vm: SeekerProfileTabViewModel = assistoViewModel()) {
    val user by vm.user.collectAsStateWithLifecycle()
    Scaffold(topBar = { AssistoTopBar("Profile") }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(user?.avatarUrl, null, Modifier.size(96.dp).clip(RoundedCornerShape(48.dp)), contentScale = ContentScale.Crop)
            Spacer(Modifier.height(12.dp))
            Text(user?.fullName ?: "", style = MaterialTheme.typography.headlineMedium)
            Text(user?.email ?: "", style = MaterialTheme.typography.bodyMedium)
            Text(user?.phone ?: "", style = MaterialTheme.typography.bodyMedium)
            Text(user?.city ?: "", style = MaterialTheme.typography.bodySmall)
        }
    }
}

private fun statusColor(status: RequestStatus) = when (status) {
    RequestStatus.COMPLETED -> SuccessGreen
    RequestStatus.CANCELLED -> AccentRed
    RequestStatus.PENDING -> WarningOrange
    else -> SecondaryBlue
}
