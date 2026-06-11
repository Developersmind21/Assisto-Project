package com.example.assisto.data.model

enum class ServiceCategory(val displayName: String, val accentColorHex: Long) {
    HOME("Home Services", 0xFF1565C0),
    AUTO("Auto & Mechanical", 0xFF6A1B9A),
    TECH("Tech Support", 0xFF00838F),
    EDUCATION("Education/Tutoring", 0xFFEF6C00),
    PERSONAL("Personal Assistance", 0xFF2E7D32),
}

enum class RequestStatus {
    PENDING, ACCEPTED, EN_ROUTE, ARRIVED, IN_PROGRESS, COMPLETED, CANCELLED,
}

enum class BudgetRange(val label: String) {
    NO_PREFERENCE("No preference"),
    UNDER_50("Under $50"),
    RANGE_50_150("$50–$150"),
    OVER_150("$150+"),
}

enum class TimingOption(val label: String) {
    RIGHT_NOW("Right now"),
    SCHEDULED("Schedule for later"),
}

enum class SortOption(val label: String) {
    DISTANCE("Distance"),
    RATING("Rating"),
    PRICE("Price"),
    AVAILABILITY("Availability"),
}

data class ServiceSubCategory(
    val id: String,
    val name: String,
    val category: ServiceCategory,
)

data class Provider(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val rating: Float,
    val reviewCount: Int,
    val verified: Boolean,
    val skills: List<String>,
    val topSkill: String,
    val distanceMiles: Float,
    val etaMinutes: Int,
    val priceRange: String,
    val bio: String,
    val categories: List<ServiceCategory>,
    val responseTimeMinutes: Int,
    val memberSince: String,
    val jobsCompleted: Int,
    val galleryUrls: List<String>,
    val isAvailable: Boolean = true,
)

data class Review(
    val id: String,
    val reviewerName: String,
    val reviewerAvatarUrl: String,
    val rating: Float,
    val comment: String,
    val date: String,
)

data class ServiceRequest(
    val id: String,
    val category: ServiceCategory,
    val subCategory: String,
    val description: String,
    val photoUrls: List<String> = emptyList(),
    val address: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val timing: TimingOption,
    val scheduledDateTime: String? = null,
    val budget: BudgetRange,
    val status: RequestStatus,
    val createdAt: String,
    val providerId: String? = null,
    val providerName: String? = null,
    val estimatedPay: String? = null,
)

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val timestamp: String,
    val isFromSeeker: Boolean,
    val isSystem: Boolean = false,
    val imageUrl: String? = null,
)

data class Conversation(
    val id: String,
    val participantName: String,
    val participantAvatarUrl: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int,
    val isOnline: Boolean,
)

data class EarningEntry(
    val id: String,
    val date: String,
    val serviceType: String,
    val amount: Double,
    val status: RequestStatus,
)

data class PaymentSummary(
    val serviceType: String,
    val providerName: String,
    val duration: String,
    val date: String,
    val baseFee: Double,
    val platformFee: Double,
    val total: Double,
)

data class IncomingJobRequest(
    val id: String,
    val category: ServiceCategory,
    val subCategory: String,
    val description: String,
    val seekerDistanceMiles: Float,
    val estimatedPayRange: String,
    val seekerAddress: String,
    val expiresAtEpochMs: Long,
)

data class ActiveJob(
    val id: String,
    val requestId: String,
    val seekerName: String,
    val seekerAvatarUrl: String,
    val seekerPhoneMasked: String,
    val address: String,
    val distanceMiles: Float,
    val etaMinutes: Int,
    val category: ServiceCategory,
    val subCategory: String,
    val status: RequestStatus,
    val estimatedPay: String,
)

data class UserProfile(
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val zipCode: String,
    val city: String,
    val role: UserRole,
    val avatarUrl: String = "",
    val bio: String = "",
    val primaryCategory: String = "",
    val skills: List<String> = emptyList(),
    val kycVerified: Boolean = false,
)
