package com.example.assisto.ui.seeker.matching

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.SortOption
import com.example.assisto.data.repository.ProviderRepository
import com.example.assisto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatchingUiState(
    val providers: UiState<List<Provider>> = UiState.Loading,
    val sortBy: SortOption = SortOption.DISTANCE,
    val radiusMiles: Float = 5f,
    val isSearching: Boolean = true,
)

class ProviderMatchingViewModel constructor(
    private val providerRepository: ProviderRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val requestId: String = savedStateHandle.get<String>("requestId") ?: ""

    private val _state = MutableStateFlow(MatchingUiState())
    val state: StateFlow<MatchingUiState> = _state.asStateFlow()

    init { search() }

    fun setSort(sort: SortOption) {
        _state.update { it.copy(sortBy = sort) }
        search()
    }

    fun expandRadius() {
        _state.update { it.copy(radiusMiles = it.radiusMiles + 5f) }
        search()
    }

    fun retry() = search()

    private fun search() {
        viewModelScope.launch {
            _state.update { it.copy(isSearching = true, providers = UiState.Loading) }
            providerRepository.getProvidersNearLocation(null, _state.value.sortBy, _state.value.radiusMiles).collect { list ->
                _state.update { it.copy(isSearching = false, providers = UiState.Success(list)) }
            }
        }
    }
}
