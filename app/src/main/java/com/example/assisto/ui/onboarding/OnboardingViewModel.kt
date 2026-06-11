package com.example.assisto.ui.onboarding

import androidx.lifecycle.ViewModel
import com.example.assisto.data.mock.MockUserRepository
import com.example.assisto.data.model.OnboardingProfile
import com.example.assisto.data.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class OnboardingViewModel(
    private val userRepository: MockUserRepository,
) : ViewModel() {

    private val _profile = MutableStateFlow(OnboardingProfile())
    val profile: StateFlow<OnboardingProfile> = _profile.asStateFlow()

    fun selectRole(role: UserRole) {
        _profile.update { it.copy(role = role) }
        userRepository.setRole(role)
    }

    fun updateFullName(value: String) = _profile.update { it.copy(fullName = value) }
    fun updateEmail(value: String) = _profile.update { it.copy(email = value) }
    fun updatePhone(value: String) = _profile.update { it.copy(phone = value) }
    fun updateZipCode(value: String) = _profile.update { it.copy(zipCode = value) }
    fun updatePrimaryCategory(value: String) = _profile.update { it.copy(primaryCategory = value) }
    fun updateBio(value: String) = _profile.update { it.copy(bio = value) }

    fun completeSeekerOnboarding() {
        userRepository.setSeekerName(_profile.value.fullName)
    }

    fun completeProviderOnboarding() {
        val p = _profile.value
        userRepository.setProviderFromOnboarding(p.fullName, p.primaryCategory, p.bio)
    }

    fun isSeekerProfileValid(): Boolean {
        val c = _profile.value
        return c.fullName.isNotBlank() && c.email.isNotBlank() && c.phone.isNotBlank() && c.zipCode.length >= 5
    }

    fun isProviderProfileValid(): Boolean {
        val c = _profile.value
        return isSeekerProfileValid() && c.primaryCategory.isNotBlank() && c.bio.isNotBlank()
    }
}
