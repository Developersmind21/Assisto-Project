package com.example.assisto.ui.provider.jobs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.repository.RequestRepository
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.LoadingShimmerList
import com.example.assisto.ui.components.StatusChip
import com.example.assisto.ui.theme.SecondaryBlue
import com.example.assisto.ui.theme.SuccessGreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProviderJobsViewModel constructor(private val repo: RequestRepository) : ViewModel() {
    private val _jobs = MutableStateFlow<UiState<List<ServiceRequest>>>(UiState.Loading)
    val jobs: StateFlow<UiState<List<ServiceRequest>>> = _jobs.asStateFlow()
    init {
        viewModelScope.launch {
            repo.getMyRequests().collect { list ->
                _jobs.value = UiState.Success(list.filter { it.status != RequestStatus.PENDING })
            }
        }
    }
}

@Composable
fun ProviderJobsScreen(onJobClick: (String) -> Unit, viewModel: ProviderJobsViewModel = assistoViewModel()) {
    val jobs by viewModel.jobs.collectAsStateWithLifecycle()
    Scaffold(topBar = { AssistoTopBar("Jobs") }) { padding ->
        when (val s = jobs) {
            is UiState.Loading -> LoadingShimmerList(3)
            is UiState.Success -> LazyColumn(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(s.data) { job ->
                    AssistoCard(onClick = { onJobClick(job.id) }) {
                        StatusChip(job.status.name.replace('_', ' '), if (job.status == RequestStatus.COMPLETED) SuccessGreen else SecondaryBlue)
                        Text(job.subCategory, style = MaterialTheme.typography.titleMedium)
                        Text(job.description.take(80), style = MaterialTheme.typography.bodySmall)
                        job.estimatedPay?.let { Text(it, style = MaterialTheme.typography.labelLarge) }
                    }
                }
            }
            is UiState.Error -> Text(s.message, Modifier.padding(20.dp))
        }
    }
}
