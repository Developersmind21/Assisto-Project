package com.example.assisto.ui.seeker.tracking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.repository.ProviderRepository
import com.example.assisto.data.repository.RequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TrackingUiState(
    val request: ServiceRequest? = null,
    val provider: Provider? = null,
    val etaMinutes: Int = 8,
    val revealedSteps: Int = 1,
    val showCancelDialog: Boolean = false,
)

class RequestTrackingViewModel constructor(
    private val requestRepository: RequestRepository,
    private val providerRepository: ProviderRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val requestId: String = savedStateHandle.get<String>("requestId") ?: ""
    private val _state = MutableStateFlow(TrackingUiState())
    val state: StateFlow<TrackingUiState> = _state.asStateFlow()

    private val timeline = listOf(
        RequestStatus.ACCEPTED to "Accepted",
        RequestStatus.EN_ROUTE to "En Route",
        RequestStatus.ARRIVED to "Arrived",
        RequestStatus.IN_PROGRESS to "In Progress",
        RequestStatus.COMPLETED to "Completed",
    )

    init {
        viewModelScope.launch {
            requestRepository.getRequestById(requestId).collect { req ->
                _state.update { it.copy(request = req) }
                req?.providerId?.let { pid ->
                    providerRepository.getProviderById(pid).collect { p ->
                        _state.update { it.copy(provider = p, etaMinutes = p?.etaMinutes ?: 8) }
                    }
                }
            }
        }
        viewModelScope.launch {
            kotlinx.coroutines.delay(600)
            _state.update { it.copy(revealedSteps = 2) }
            kotlinx.coroutines.delay(800)
            _state.update { it.copy(revealedSteps = 3) }
        }
    }

    fun timelineSteps() = timeline
    fun showCancel(show: Boolean) = _state.update { it.copy(showCancelDialog = show) }
    fun cancelRequest() {
        viewModelScope.launch {
            requestRepository.updateRequestStatus(requestId, RequestStatus.CANCELLED).collect {}
        }
    }
}
