package com.example.assisto.ui.provider.activejob

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.ActiveJob
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.repository.RequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActiveJobViewModel constructor(
    private val requestRepository: RequestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val jobId: String = savedStateHandle.get<String>("requestId") ?: "job1"
    private val _job = MutableStateFlow<ActiveJob?>(null)
    val job: StateFlow<ActiveJob?> = _job.asStateFlow()

    init {
        viewModelScope.launch {
            requestRepository.getActiveJob().collect { _job.value = it }
        }
    }

    fun updateStatus(status: RequestStatus) {
        val requestId = _job.value?.requestId ?: return
        viewModelScope.launch {
            requestRepository.updateRequestStatus(requestId, status).collect {}
            _job.update { it?.copy(status = status) }
        }
    }
}
