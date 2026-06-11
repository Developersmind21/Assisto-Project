package com.example.assisto.data.mock

import com.example.assisto.data.model.EarningEntry
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.UserProfile
import com.example.assisto.data.model.UserRole
import com.example.assisto.data.repository.EarningsData
import com.example.assisto.data.repository.EarningsPeriod
import com.example.assisto.data.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
class MockUserRepository : UserRepository {

    private val seekerProfile = MutableStateFlow(MockData.currentSeeker)
    private val providerProfile = MutableStateFlow(MockData.currentProvider)
    var activeRole: UserRole = UserRole.SEEKER

    override fun getCurrentUser(): Flow<UserProfile> = flow {
        emit(if (activeRole == UserRole.SEEKER) seekerProfile.value else providerProfile.value)
    }

    override fun updateProfile(profile: UserProfile): Flow<UserProfile> = flow {
        delay(300)
        if (profile.role == UserRole.SEEKER) seekerProfile.value = profile
        else providerProfile.value = profile
        emit(profile)
    }

    override fun getEarnings(period: EarningsPeriod): Flow<EarningsData> = flow {
        delay(400)
        val entries = MockData.earnings
        val total = when (period) {
            EarningsPeriod.TODAY -> entries.firstOrNull()?.amount ?: 0.0
            EarningsPeriod.THIS_WEEK -> entries.sumOf { it.amount }
            EarningsPeriod.THIS_MONTH -> entries.sumOf { it.amount } * 4
        }
        emit(
            EarningsData(
                total = total,
                nextPayoutAmount = 385.0,
                nextPayoutDate = "Mar 10, 2026",
                entries = entries,
                chartValues = listOf(75f, 110f, 185f, 120f, 95f),
                chartLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri"),
            ),
        )
    }

    fun setRole(role: UserRole) {
        activeRole = role
    }

    fun setSeekerName(name: String) {
        seekerProfile.value = seekerProfile.value.copy(fullName = name)
    }

    fun setProviderFromOnboarding(name: String, category: String, bio: String) {
        providerProfile.value = providerProfile.value.copy(
            fullName = name,
            primaryCategory = category,
            bio = bio,
        )
    }
}
