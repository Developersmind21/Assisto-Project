package com.example.assisto.ui.seeker.rating

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.repository.RequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RatingUiState(
    val rating: Int = 0,
    val selectedTags: Set<String> = emptySet(),
    val review: String = "",
    val isSubmitting: Boolean = false,
)

class RatingViewModel constructor(
    private val requestRepository: RequestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val requestId: String = savedStateHandle.get<String>("requestId") ?: ""
    private val _state = MutableStateFlow(RatingUiState())
    val state: StateFlow<RatingUiState> = _state.asStateFlow()

    val availableTags = listOf("Quick response", "Professional", "Great work", "Friendly", "On time")

    fun setRating(r: Int) = _state.update { it.copy(rating = r) }
    fun toggleTag(tag: String) = _state.update {
        val tags = it.selectedTags.toMutableSet()
        if (tag in tags) tags.remove(tag) else tags.add(tag)
        it.copy(selectedTags = tags)
    }
    fun setReview(v: String) = _state.update { it.copy(review = v) }

    fun submit(onDone: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true) }
            requestRepository.submitReview(requestId, _state.value.rating, _state.value.selectedTags.toList(), _state.value.review)
            _state.update { it.copy(isSubmitting = false) }
            onDone()
        }
    }

    fun skip(onDone: () -> Unit) = onDone()
}
