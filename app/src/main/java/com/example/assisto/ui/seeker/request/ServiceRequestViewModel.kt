package com.example.assisto.ui.seeker.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.mock.MockData
import com.example.assisto.data.model.BudgetRange
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.TimingOption
import com.example.assisto.data.repository.RequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ServiceRequestUiState(
    val step: Int = 1,
    val category: ServiceCategory = ServiceCategory.HOME,
    val subCategory: String = "",
    val description: String = "",
    val photoCount: Int = 0,
    val address: String = "1234 Barton Springs Rd, Austin, TX 78704",
    val timing: TimingOption = TimingOption.RIGHT_NOW,
    val scheduledDateTime: String = "",
    val budget: BudgetRange = BudgetRange.NO_PREFERENCE,
    val isSubmitting: Boolean = false,
    val createdRequestId: String? = null,
)

class ServiceRequestViewModel constructor(
    private val requestRepository: RequestRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ServiceRequestUiState())
    val state: StateFlow<ServiceRequestUiState> = _state.asStateFlow()

    fun initCategory(categoryName: String?) {
        if (categoryName == null) return
        val cat = ServiceCategory.entries.find { it.name == categoryName } ?: return
        val subs = requestRepository.getSubCategories(cat)
        _state.update { it.copy(category = cat, subCategory = subs.firstOrNull()?.name ?: "") }
    }

    fun subCategories() = requestRepository.getSubCategories(_state.value.category)

    fun nextStep() = _state.update { it.copy(step = (it.step + 1).coerceAtMost(3)) }
    fun prevStep() = _state.update { it.copy(step = (it.step - 1).coerceAtLeast(1)) }
    fun setSubCategory(v: String) = _state.update { it.copy(subCategory = v) }
    fun setDescription(v: String) {
        if (v.length <= 300) _state.update { it.copy(description = v) }
    }
    fun addPhoto() = _state.update { if (it.photoCount < 3) it.copy(photoCount = it.photoCount + 1) else it }
    fun setTiming(v: TimingOption) = _state.update { it.copy(timing = v) }
    fun setBudget(v: BudgetRange) = _state.update { it.copy(budget = v) }
    fun setScheduled(v: String) = _state.update { it.copy(scheduledDateTime = v) }
    fun useCurrentLocation() = _state.update { it.copy(address = "1234 Barton Springs Rd, Austin, TX 78704") }

    fun submit(onCreated: (String) -> Unit) {
        val s = _state.value
        if (s.description.isBlank() || s.subCategory.isBlank()) return
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true) }
            val request = ServiceRequest(
                id = "req_${System.currentTimeMillis()}",
                category = s.category,
                subCategory = s.subCategory,
                description = s.description,
                address = s.address,
                city = MockData.DEFAULT_CITY,
                latitude = MockData.DEFAULT_LAT,
                longitude = MockData.DEFAULT_LNG,
                timing = s.timing,
                scheduledDateTime = s.scheduledDateTime.ifBlank { null },
                budget = s.budget,
                status = RequestStatus.PENDING,
                createdAt = "Today",
            )
            requestRepository.createRequest(request).collect { created ->
                _state.update { it.copy(isSubmitting = false, createdRequestId = created.id) }
                onCreated(created.id)
            }
        }
    }
}
