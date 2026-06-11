package com.example.assisto.ui.seeker.providerdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.Review
import com.example.assisto.data.repository.ProviderRepository
import com.example.assisto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProviderDetailUiState(
    val provider: UiState<Provider> = UiState.Loading,
    val reviews: UiState<List<Review>> = UiState.Loading,
    val selectedTab: Int = 0,
)

class ProviderDetailViewModel constructor(
    private val providerRepository: ProviderRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val providerId: String = savedStateHandle.get<String>("providerId") ?: ""
    private val _state = MutableStateFlow(ProviderDetailUiState())
    val state: StateFlow<ProviderDetailUiState> = _state.asStateFlow()

    init { load() }

    fun selectTab(index: Int) = _state.update { it.copy(selectedTab = index) }

    private fun load() {
        viewModelScope.launch {
            providerRepository.getProviderById(providerId).collect { p ->
                _state.update { it.copy(provider = if (p != null) UiState.Success(p) else UiState.Error("Not found")) }
            }
        }
        viewModelScope.launch {
            providerRepository.getProviderReviews(providerId).collect { r ->
                _state.update { it.copy(reviews = UiState.Success(r)) }
            }
        }
    }
}
