package com.example.assisto.ui.provider.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assisto.data.model.UserProfile
import com.example.assisto.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProviderProfileEditViewModel constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getCurrentUser().collect { _profile.value = it }
        }
    }

    fun updateName(v: String) { _profile.value = _profile.value?.copy(fullName = v) }
    fun updateBio(v: String) { _profile.value = _profile.value?.copy(bio = v) }
    fun addSkill(skill: String) {
        _profile.value = _profile.value?.copy(skills = _profile.value!!.skills + skill)
    }
    fun removeSkill(skill: String) {
        _profile.value = _profile.value?.copy(skills = _profile.value!!.skills - skill)
    }

    fun save() {
        val p = _profile.value ?: return
        viewModelScope.launch { userRepository.updateProfile(p).collect {} }
    }
}
