package com.example.assisto.data.repository

import com.example.assisto.data.model.EarningEntry
import com.example.assisto.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<UserProfile>
    fun updateProfile(profile: UserProfile): Flow<UserProfile>
    fun getEarnings(period: EarningsPeriod): Flow<EarningsData>
}

enum class EarningsPeriod { TODAY, THIS_WEEK, THIS_MONTH }

data class EarningsData(
    val total: Double,
    val nextPayoutAmount: Double,
    val nextPayoutDate: String,
    val entries: List<EarningEntry>,
    val chartValues: List<Float>,
    val chartLabels: List<String>,
)
