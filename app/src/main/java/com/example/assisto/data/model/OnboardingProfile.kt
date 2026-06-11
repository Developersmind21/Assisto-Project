package com.example.assisto.data.model

data class OnboardingProfile(
    val role: UserRole? = null,
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val zipCode: String = "",
    val primaryCategory: String = "",
    val bio: String = "",
)
