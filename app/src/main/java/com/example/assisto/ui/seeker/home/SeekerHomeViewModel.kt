package com.example.assisto.ui.seeker.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.UserProfile
import com.example.assisto.data.repository.ProviderRepository
import com.example.assisto.data.repository.RequestRepository
import com.example.assisto.data.repository.UserRepository
import com.example.assisto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
data class SeekerHomeUiState(
    val user: UserProfile? = null,
    val recentRequests: UiState<List<ServiceRequest>> = UiState.Loading,
    val featuredProviders: UiState<List<Provider>> = UiState.Loading,
)

class SeekerHomeViewModel(
    private val userRepository: UserRepository,
    private val requestRepository: RequestRepository,
    private val providerRepository: ProviderRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeekerHomeUiState())
    val uiState: StateFlow<SeekerHomeUiState> = _uiState.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            combine(
                userRepository.getCurrentUser(),
                requestRepository.getRecentRequests().catch { emit(emptyList()) },
                providerRepository.getFeaturedProviders().catch { emit(emptyList()) },
            ) { user, requests, providers ->
                SeekerHomeUiState(
                    user = user,
                    recentRequests = UiState.Success(requests),
                    featuredProviders = UiState.Success(providers),
                )
            }.catch {
                _uiState.value = _uiState.value.copy(
                    recentRequests = UiState.Error(it.message ?: "Failed to load"),
                )
            }.collect { _uiState.value = it }
        }
    }
}
