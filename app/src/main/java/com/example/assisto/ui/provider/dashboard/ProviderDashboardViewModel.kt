package com.example.assisto.ui.provider.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.mock.MockRequestRepository
import com.example.assisto.data.model.ActiveJob
import com.example.assisto.data.model.IncomingJobRequest
import com.example.assisto.data.model.UserProfile
import com.example.assisto.data.repository.EarningsPeriod
import com.example.assisto.data.repository.RequestRepository
import com.example.assisto.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProviderDashboardUiState(
    val user: UserProfile? = null,
    val isAvailable: Boolean = false,
    val activeJob: ActiveJob? = null,
    val incomingRequests: List<IncomingJobRequest> = emptyList(),
    val jobsToday: Int = 2,
    val earningsToday: Double = 95.0,
    val avgRating: Float = 4.9f,
    val weeklyChart: List<Float> = listOf(75f, 110f, 185f, 120f, 95f),
)

class ProviderDashboardViewModel constructor(
    private val userRepository: UserRepository,
    private val requestRepository: RequestRepository,
    private val mockRequestRepository: MockRequestRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProviderDashboardUiState())
    val state: StateFlow<ProviderDashboardUiState> = _state.asStateFlow()

    init { load() }

    private fun load() {
        viewModelScope.launch {
            userRepository.getCurrentUser().collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
        viewModelScope.launch {
            requestRepository.getActiveJob().collect { job ->
                _state.update { it.copy(activeJob = job) }
            }
        }
        viewModelScope.launch {
            requestRepository.getIncomingRequests().collect { incoming ->
                _state.update { it.copy(incomingRequests = incoming) }
            }
        }
        viewModelScope.launch {
            userRepository.getEarnings(EarningsPeriod.TODAY).collect { e ->
                _state.update { it.copy(earningsToday = e.total, weeklyChart = e.chartValues) }
            }
        }
    }

    fun toggleAvailability() {
        _state.update { it.copy(isAvailable = !it.isAvailable) }
        if (_state.value.isAvailable) {
            mockRequestRepository.simulateIncomingRequest()
        }
    }

    fun acceptRequest(id: String) {
        mockRequestRepository.clearIncomingRequest(id)
        _state.update { it.copy(incomingRequests = it.incomingRequests.filter { r -> r.id != id }) }
    }

    fun declineRequest(id: String) {
        mockRequestRepository.clearIncomingRequest(id)
        _state.update { it.copy(incomingRequests = it.incomingRequests.filter { r -> r.id != id }) }
    }
}
