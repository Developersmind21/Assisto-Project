package com.example.assisto.ui.seeker.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.PaymentSummary
import com.example.assisto.data.repository.RequestRepository
import com.example.assisto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PaymentUiState(
    val summary: UiState<PaymentSummary> = UiState.Loading,
    val selectedMethod: PaymentMethod = PaymentMethod.SAVED_CARD,
    val isProcessing: Boolean = false,
    val paymentSuccess: Boolean = false,
)

enum class PaymentMethod(val label: String) {
    SAVED_CARD("Visa •••• 4242"),
    GOOGLE_PAY("Google Pay"),
    NEW_CARD("Add new card"),
}

class JobCompletionViewModel constructor(
    private val requestRepository: RequestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val requestId: String = savedStateHandle.get<String>("requestId") ?: ""
    private val _state = MutableStateFlow(PaymentUiState())
    val state: StateFlow<PaymentUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            requestRepository.getPaymentSummary(requestId).collect { s ->
                _state.update { it.copy(summary = UiState.Success(s)) }
            }
        }
    }

    fun selectMethod(m: PaymentMethod) = _state.update { it.copy(selectedMethod = m) }

    fun confirmPayment(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isProcessing = true) }
            kotlinx.coroutines.delay(1200)
            _state.update { it.copy(isProcessing = false, paymentSuccess = true) }
            onSuccess()
        }
    }
}
