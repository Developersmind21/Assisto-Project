package com.example.assisto.data.mock

import com.example.assisto.data.model.ActiveJob
import com.example.assisto.data.model.BudgetRange
import com.example.assisto.data.model.ChatMessage
import com.example.assisto.data.model.Conversation
import com.example.assisto.data.model.EarningEntry
import com.example.assisto.data.model.IncomingJobRequest
import com.example.assisto.data.model.PaymentSummary
import com.example.assisto.data.model.Provider
import com.example.assisto.data.model.RequestStatus
import com.example.assisto.data.model.Review
import com.example.assisto.data.model.ServiceCategory
import com.example.assisto.data.model.ServiceRequest
import com.example.assisto.data.model.ServiceSubCategory
import com.example.assisto.data.model.TimingOption
import com.example.assisto.data.model.UserProfile
import com.example.assisto.data.model.UserRole

object MockData {

    const val DEFAULT_CITY = "Austin, TX"
    const val DEFAULT_LAT = 30.2672
    const val DEFAULT_LNG = -97.7431

    val subCategories = mapOf(
        ServiceCategory.HOME to listOf("Plumbing", "Electrical", "Carpentry", "Cleaning", "HVAC", "Pest Control"),
        ServiceCategory.AUTO to listOf("Roadside Assistance", "Tire Change", "Battery Jump", "Mobile Detailing", "Light Repairs"),
        ServiceCategory.TECH to listOf("Device Setup", "Software/Networking", "Smart Home", "Security Cameras"),
        ServiceCategory.EDUCATION to listOf("K-12 Subjects", "Test Prep (SAT/ACT)", "Languages", "Music", "College Counseling"),
        ServiceCategory.PERSONAL to listOf("Errands", "Moving Help", "Grocery/Delivery", "Event Help", "Senior Care Support"),
    )

    val providers = listOf(
        Provider(
            id = "p1",
            name = "Marcus Rivera",
            avatarUrl = "https://i.pravatar.cc/150?u=marcus",
            rating = 4.9f,
            reviewCount = 127,
            verified = true,
            skills = listOf("Plumbing", "Pipe Repair", "Water Heaters"),
            topSkill = "Plumbing",
            distanceMiles = 1.2f,
            etaMinutes = 8,
            priceRange = "$75–$150",
            bio = "Licensed plumber with 12 years of experience serving Austin metro. EPA certified, fully insured.",
            categories = listOf(ServiceCategory.HOME),
            responseTimeMinutes = 5,
            memberSince = "March 2023",
            jobsCompleted = 342,
            galleryUrls = listOf(
                "https://picsum.photos/seed/p1a/400/300",
                "https://picsum.photos/seed/p1b/400/300",
                "https://picsum.photos/seed/p1c/400/300",
            ),
        ),
        Provider(
            id = "p2",
            name = "Sarah Chen",
            avatarUrl = "https://i.pravatar.cc/150?u=sarah",
            rating = 4.8f,
            reviewCount = 89,
            verified = true,
            skills = listOf("Smart Home", "Wi-Fi Setup", "Device Setup"),
            topSkill = "Tech Support",
            distanceMiles = 0.8f,
            etaMinutes = 6,
            priceRange = "$50–$100",
            bio = "IT professional specializing in home network setup and smart device installation.",
            categories = listOf(ServiceCategory.TECH),
            responseTimeMinutes = 3,
            memberSince = "June 2023",
            jobsCompleted = 198,
            galleryUrls = listOf(
                "https://picsum.photos/seed/p2a/400/300",
                "https://picsum.photos/seed/p2b/400/300",
            ),
        ),
        Provider(
            id = "p3",
            name = "James Whitfield",
            avatarUrl = "https://i.pravatar.cc/150?u=james",
            rating = 4.7f,
            reviewCount = 56,
            verified = true,
            skills = listOf("Roadside Assistance", "Tire Change", "Battery Jump"),
            topSkill = "Roadside Help",
            distanceMiles = 2.4f,
            etaMinutes = 12,
            priceRange = "$40–$80",
            bio = "Mobile mechanic available 24/7 for roadside emergencies across Austin.",
            categories = listOf(ServiceCategory.AUTO),
            responseTimeMinutes = 8,
            memberSince = "January 2024",
            jobsCompleted = 87,
            galleryUrls = listOf("https://picsum.photos/seed/p3a/400/300"),
        ),
        Provider(
            id = "p4",
            name = "Emily Nakamura",
            avatarUrl = "https://i.pravatar.cc/150?u=emily",
            rating = 5.0f,
            reviewCount = 43,
            verified = true,
            skills = listOf("SAT Prep", "Math Tutoring", "College Counseling"),
            topSkill = "Test Prep",
            distanceMiles = 3.1f,
            etaMinutes = 15,
            priceRange = "$60–$120/hr",
            bio = "Former high school math teacher with 8 years of SAT/ACT prep experience.",
            categories = listOf(ServiceCategory.EDUCATION),
            responseTimeMinutes = 10,
            memberSince = "September 2023",
            jobsCompleted = 112,
            galleryUrls = emptyList(),
        ),
        Provider(
            id = "p5",
            name = "David Okonkwo",
            avatarUrl = "https://i.pravatar.cc/150?u=david",
            rating = 4.6f,
            reviewCount = 71,
            verified = false,
            skills = listOf("Errands", "Moving Help", "Grocery Delivery"),
            topSkill = "Personal Assistance",
            distanceMiles = 1.8f,
            etaMinutes = 10,
            priceRange = "$30–$60",
            bio = "Reliable helper for errands, moving, and daily tasks. Background check pending.",
            categories = listOf(ServiceCategory.PERSONAL),
            responseTimeMinutes = 7,
            memberSince = "November 2024",
            jobsCompleted = 45,
            galleryUrls = emptyList(),
            isAvailable = false,
        ),
    )

    val reviews = mapOf(
        "p1" to listOf(
            Review("r1", "Jennifer L.", "https://i.pravatar.cc/150?u=jennifer", 5f, "Fixed our leak in under an hour. Very professional and clean work.", "Feb 12, 2026"),
            Review("r2", "Robert M.", "https://i.pravatar.cc/150?u=robert", 4.5f, "Great plumber, arrived on time. Fair pricing.", "Jan 28, 2026"),
        ),
        "p2" to listOf(
            Review("r3", "Amy K.", "https://i.pravatar.cc/150?u=amy", 5f, "Set up our entire smart home in one visit. Highly recommend!", "Mar 1, 2026"),
        ),
    )

    val recentRequests = listOf(
        ServiceRequest("req1", ServiceCategory.HOME, "Plumbing", "Kitchen sink leaking", emptyList(), "1234 Barton Springs Rd", DEFAULT_CITY, DEFAULT_LAT, DEFAULT_LNG, TimingOption.RIGHT_NOW, null, BudgetRange.RANGE_50_150, RequestStatus.IN_PROGRESS, "Mar 5, 2026", "p1", "Marcus Rivera", "$95"),
        ServiceRequest("req2", ServiceCategory.TECH, "Device Setup", "New router installation", emptyList(), "5678 South Lamar Blvd", DEFAULT_CITY, DEFAULT_LAT + 0.01, DEFAULT_LNG, TimingOption.SCHEDULED, "Mar 8, 2026 2:00 PM", BudgetRange.UNDER_50, RequestStatus.PENDING, "Mar 4, 2026"),
        ServiceRequest("req3", ServiceCategory.AUTO, "Battery Jump", "Car won't start in parking lot", emptyList(), "9012 Congress Ave", DEFAULT_CITY, DEFAULT_LAT - 0.01, DEFAULT_LNG + 0.01, TimingOption.RIGHT_NOW, null, BudgetRange.UNDER_50, RequestStatus.COMPLETED, "Feb 28, 2026", "p3", "James Whitfield", "$55"),
    )

    val conversations = listOf(
        Conversation("conv1", "Marcus Rivera", "https://i.pravatar.cc/150?u=marcus", "On my way now!", "2:34 PM", 1, true),
        Conversation("conv2", "Sarah Chen", "https://i.pravatar.cc/150?u=sarah", "Router is all set up.", "Yesterday", 0, false),
    )

    val chatMessages = mapOf(
        "conv1" to listOf(
            ChatMessage("m0", "conv1", "system", "Assisto", "Provider accepted your request", "2:10 PM", false, isSystem = true),
            ChatMessage("m1", "conv1", "seeker", "You", "Hi Marcus, the leak is under the kitchen sink.", "2:12 PM", true),
            ChatMessage("m2", "conv1", "p1", "Marcus Rivera", "Got it. I have the parts I need. Heading over now.", "2:15 PM", false),
            ChatMessage("m3", "conv1", "system", "Assisto", "Provider is on the way", "2:20 PM", false, isSystem = true),
            ChatMessage("m4", "conv1", "p1", "Marcus Rivera", "On my way now!", "2:34 PM", false),
        ),
    )

    val currentSeeker = UserProfile(
        id = "u1",
        fullName = "Alex Thompson",
        email = "alex.thompson@email.com",
        phone = "+1 (512) 555-0142",
        zipCode = "78704",
        city = DEFAULT_CITY,
        role = UserRole.SEEKER,
        avatarUrl = "https://i.pravatar.cc/150?u=alex",
    )

    val currentProvider = UserProfile(
        id = "u2",
        fullName = "Marcus Rivera",
        email = "marcus.rivera@email.com",
        phone = "+1 (512) 555-0198",
        zipCode = "78745",
        city = DEFAULT_CITY,
        role = UserRole.PROVIDER,
        avatarUrl = "https://i.pravatar.cc/150?u=marcus",
        bio = "Licensed plumber with 12 years of experience.",
        primaryCategory = "Home Services",
        skills = listOf("Plumbing", "Pipe Repair", "Water Heaters"),
        kycVerified = true,
    )

    fun incomingRequest() = IncomingJobRequest(
        id = "inc1",
        category = ServiceCategory.HOME,
        subCategory = "Plumbing",
        description = "Kitchen sink is leaking badly, need help ASAP",
        seekerDistanceMiles = 1.4f,
        estimatedPayRange = "$75–$120",
        seekerAddress = "1234 Barton Springs Rd, Austin, TX",
        expiresAtEpochMs = System.currentTimeMillis() + 90_000,
    )

    val activeJob = ActiveJob(
        id = "job1",
        requestId = "req1",
        seekerName = "Alex Thompson",
        seekerAvatarUrl = "https://i.pravatar.cc/150?u=alex",
        seekerPhoneMasked = "+1 (512) ***-0142",
        address = "1234 Barton Springs Rd, Austin, TX 78704",
        distanceMiles = 1.2f,
        etaMinutes = 8,
        category = ServiceCategory.HOME,
        subCategory = "Plumbing",
        status = RequestStatus.EN_ROUTE,
        estimatedPay = "$95",
    )

    val earnings = listOf(
        EarningEntry("e1", "Mar 5, 2026", "Plumbing", 95.0, RequestStatus.IN_PROGRESS),
        EarningEntry("e2", "Mar 4, 2026", "Pipe Repair", 120.0, RequestStatus.COMPLETED),
        EarningEntry("e3", "Mar 3, 2026", "Water Heater", 185.0, RequestStatus.COMPLETED),
        EarningEntry("e4", "Mar 2, 2026", "Drain Cleaning", 75.0, RequestStatus.COMPLETED),
        EarningEntry("e5", "Mar 1, 2026", "Plumbing", 110.0, RequestStatus.COMPLETED),
    )

    fun paymentSummary(requestId: String) = PaymentSummary(
        serviceType = "Plumbing — Kitchen Sink Leak",
        providerName = "Marcus Rivera",
        duration = "1 hr 15 min",
        date = "Mar 5, 2026",
        baseFee = 85.0,
        platformFee = 12.75,
        total = 97.75,
    )

    fun subCategoryList(category: ServiceCategory) =
        subCategories[category]?.map { ServiceSubCategory("${category.name}_$it", it, category) } ?: emptyList()
}
