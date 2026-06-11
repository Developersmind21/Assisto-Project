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

    // 8 providers, realistic US names, Austin TX, pravatar img=N avatars.
    val providers = listOf(
        Provider(
            id = "p1",
            name = "Marcus Rivera",
            avatarUrl = "https://i.pravatar.cc/150?img=12",
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
            avatarUrl = "https://i.pravatar.cc/150?img=5",
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
            avatarUrl = "https://i.pravatar.cc/150?img=15",
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
            avatarUrl = "https://i.pravatar.cc/150?img=9",
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
            avatarUrl = "https://i.pravatar.cc/150?img=33",
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
        ),
        Provider(
            id = "p6",
            name = "Olivia Martinez",
            avatarUrl = "https://i.pravatar.cc/150?img=20",
            rating = 4.9f,
            reviewCount = 154,
            verified = true,
            skills = listOf("House Cleaning", "Deep Cleaning", "Move-out Cleaning"),
            topSkill = "Cleaning",
            distanceMiles = 1.0f,
            etaMinutes = 9,
            priceRange = "$45–$90",
            bio = "Detail-oriented home cleaner serving Austin families for 6 years. Eco-friendly products.",
            categories = listOf(ServiceCategory.HOME),
            responseTimeMinutes = 4,
            memberSince = "May 2022",
            jobsCompleted = 410,
            galleryUrls = listOf(
                "https://picsum.photos/seed/p6a/400/300",
                "https://picsum.photos/seed/p6b/400/300",
            ),
        ),
        Provider(
            id = "p7",
            name = "Tyler Brooks",
            avatarUrl = "https://i.pravatar.cc/150?img=51",
            rating = 4.5f,
            reviewCount = 38,
            verified = true,
            skills = listOf("Security Cameras", "Networking", "Software/Networking"),
            topSkill = "Security Cameras",
            distanceMiles = 2.9f,
            etaMinutes = 14,
            priceRange = "$70–$140",
            bio = "Smart-home and security camera installer. Certified in major brands.",
            categories = listOf(ServiceCategory.TECH),
            responseTimeMinutes = 9,
            memberSince = "February 2024",
            jobsCompleted = 64,
            galleryUrls = listOf("https://picsum.photos/seed/p7a/400/300"),
        ),
        Provider(
            id = "p8",
            name = "Priya Patel",
            avatarUrl = "https://i.pravatar.cc/150?img=44",
            rating = 4.8f,
            reviewCount = 96,
            verified = true,
            skills = listOf("Languages", "Spanish", "K-12 Subjects"),
            topSkill = "Language Tutoring",
            distanceMiles = 4.2f,
            etaMinutes = 18,
            priceRange = "$40–$80/hr",
            bio = "Bilingual tutor offering Spanish and K-12 subject help for all ages.",
            categories = listOf(ServiceCategory.EDUCATION),
            responseTimeMinutes = 12,
            memberSince = "August 2023",
            jobsCompleted = 138,
            galleryUrls = emptyList(),
        ),
    )

    // 5 seekers.
    val seekers = listOf(
        UserProfile("u1", "Alex Thompson", "alex.thompson@email.com", "(512) 555-0142", "78704", DEFAULT_CITY, UserRole.SEEKER, avatarUrl = "https://i.pravatar.cc/150?img=3"),
        UserProfile("u3", "Jennifer Lewis", "jennifer.lewis@email.com", "(512) 555-0177", "78702", DEFAULT_CITY, UserRole.SEEKER, avatarUrl = "https://i.pravatar.cc/150?img=10"),
        UserProfile("u4", "Robert Miller", "robert.miller@email.com", "(512) 555-0188", "78745", DEFAULT_CITY, UserRole.SEEKER, avatarUrl = "https://i.pravatar.cc/150?img=14"),
        UserProfile("u5", "Amy Kowalski", "amy.kowalski@email.com", "(512) 555-0199", "78751", DEFAULT_CITY, UserRole.SEEKER, avatarUrl = "https://i.pravatar.cc/150?img=16"),
        UserProfile("u6", "Daniel Garcia", "daniel.garcia@email.com", "(512) 555-0123", "78723", DEFAULT_CITY, UserRole.SEEKER, avatarUrl = "https://i.pravatar.cc/150?img=18"),
    )

    val reviews = mapOf(
        "p1" to listOf(
            Review("r1", "Jennifer L.", "https://i.pravatar.cc/150?img=10", 5f, "Fixed our leak in under an hour. Very professional and clean work.", "Feb 12, 2026"),
            Review("r2", "Robert M.", "https://i.pravatar.cc/150?img=14", 4.5f, "Great plumber, arrived on time. Fair pricing.", "Jan 28, 2026"),
        ),
        "p2" to listOf(
            Review("r3", "Amy K.", "https://i.pravatar.cc/150?img=16", 5f, "Set up our entire smart home in one visit. Highly recommend!", "Mar 1, 2026"),
        ),
        "p6" to listOf(
            Review("r4", "Daniel G.", "https://i.pravatar.cc/150?img=18", 5f, "Spotless deep clean before our move-out. Got our deposit back!", "Feb 20, 2026"),
        ),
    )

    // 10 service requests across categories and statuses.
    val recentRequests = listOf(
        ServiceRequest("req1", ServiceCategory.HOME, "Plumbing", "Kitchen sink leaking", emptyList(), "1234 Barton Springs Rd", DEFAULT_CITY, DEFAULT_LAT, DEFAULT_LNG, TimingOption.RIGHT_NOW, null, BudgetRange.RANGE_50_150, RequestStatus.IN_PROGRESS, "Mar 5, 2026", "p1", "Marcus Rivera", "$95"),
        ServiceRequest("req2", ServiceCategory.TECH, "Device Setup", "New router installation", emptyList(), "5678 South Lamar Blvd", DEFAULT_CITY, DEFAULT_LAT + 0.01, DEFAULT_LNG, TimingOption.SCHEDULED, "Mar 8, 2026 2:00 PM", BudgetRange.UNDER_50, RequestStatus.PENDING, "Mar 4, 2026"),
        ServiceRequest("req3", ServiceCategory.AUTO, "Battery Jump", "Car won't start in parking lot", emptyList(), "9012 Congress Ave", DEFAULT_CITY, DEFAULT_LAT - 0.01, DEFAULT_LNG + 0.01, TimingOption.RIGHT_NOW, null, BudgetRange.UNDER_50, RequestStatus.COMPLETED, "Feb 28, 2026", "p3", "James Whitfield", "$55"),
        ServiceRequest("req4", ServiceCategory.HOME, "Cleaning", "Deep clean 2-bed apartment", emptyList(), "210 E 6th St", DEFAULT_CITY, DEFAULT_LAT + 0.02, DEFAULT_LNG - 0.01, TimingOption.SCHEDULED, "Mar 9, 2026 10:00 AM", BudgetRange.RANGE_50_150, RequestStatus.ACCEPTED, "Mar 5, 2026", "p6", "Olivia Martinez", "$80"),
        ServiceRequest("req5", ServiceCategory.EDUCATION, "Test Prep (SAT/ACT)", "SAT math tutoring for high schooler", emptyList(), "3400 Guadalupe St", DEFAULT_CITY, DEFAULT_LAT + 0.03, DEFAULT_LNG, TimingOption.SCHEDULED, "Mar 10, 2026 4:00 PM", BudgetRange.OVER_150, RequestStatus.ACCEPTED, "Mar 4, 2026", "p4", "Emily Nakamura", "$120"),
        ServiceRequest("req6", ServiceCategory.PERSONAL, "Moving Help", "Need 2 helpers for apartment move", emptyList(), "4500 Manchaca Rd", DEFAULT_CITY, DEFAULT_LAT - 0.02, DEFAULT_LNG - 0.02, TimingOption.SCHEDULED, "Mar 12, 2026 9:00 AM", BudgetRange.RANGE_50_150, RequestStatus.PENDING, "Mar 6, 2026"),
        ServiceRequest("req7", ServiceCategory.TECH, "Security Cameras", "Install 4 outdoor cameras", emptyList(), "7800 Shoal Creek Blvd", DEFAULT_CITY, DEFAULT_LAT + 0.015, DEFAULT_LNG + 0.02, TimingOption.SCHEDULED, "Mar 11, 2026 1:00 PM", BudgetRange.OVER_150, RequestStatus.EN_ROUTE, "Mar 6, 2026", "p7", "Tyler Brooks", "$140"),
        ServiceRequest("req8", ServiceCategory.AUTO, "Tire Change", "Flat tire on highway shoulder", emptyList(), "I-35 N near Airport Blvd", DEFAULT_CITY, DEFAULT_LAT - 0.03, DEFAULT_LNG + 0.03, TimingOption.RIGHT_NOW, null, BudgetRange.UNDER_50, RequestStatus.CANCELLED, "Mar 2, 2026"),
        ServiceRequest("req9", ServiceCategory.EDUCATION, "Languages", "Spanish conversation practice", emptyList(), "1100 E Cesar Chavez St", DEFAULT_CITY, DEFAULT_LAT + 0.005, DEFAULT_LNG - 0.03, TimingOption.SCHEDULED, "Mar 13, 2026 6:00 PM", BudgetRange.RANGE_50_150, RequestStatus.COMPLETED, "Feb 25, 2026", "p8", "Priya Patel", "$60"),
        ServiceRequest("req10", ServiceCategory.HOME, "Electrical", "Replace ceiling fan and switch", emptyList(), "620 W 35th St", DEFAULT_CITY, DEFAULT_LAT + 0.04, DEFAULT_LNG + 0.01, TimingOption.RIGHT_NOW, null, BudgetRange.RANGE_50_150, RequestStatus.COMPLETED, "Feb 22, 2026", "p1", "Marcus Rivera", "$110"),
    )

    val conversations = listOf(
        Conversation("conv1", "Marcus Rivera", "https://i.pravatar.cc/150?img=12", "On my way now!", "2:34 PM", 1, true),
        Conversation("conv2", "Sarah Chen", "https://i.pravatar.cc/150?img=5", "Router is all set up. Enjoy!", "Yesterday", 0, false),
    )

    val chatMessages = mapOf(
        "conv1" to listOf(
            ChatMessage("c1m0", "conv1", "system", "Assisto", "Provider accepted your request", "2:08 PM", false, isSystem = true),
            ChatMessage("c1m1", "conv1", "seeker", "You", "Hi Marcus, the leak is under the kitchen sink.", "2:10 PM", true),
            ChatMessage("c1m2", "conv1", "p1", "Marcus Rivera", "Got it. Can you send a photo of the area?", "2:11 PM", false),
            ChatMessage("c1m3", "conv1", "seeker", "You", "Sure, here it is.", "2:12 PM", true, imageUrl = "https://picsum.photos/seed/leak/400/300"),
            ChatMessage("c1m4", "conv1", "p1", "Marcus Rivera", "Looks like a worn gasket. I have the parts.", "2:13 PM", false),
            ChatMessage("c1m5", "conv1", "seeker", "You", "Great, how soon can you come?", "2:14 PM", true),
            ChatMessage("c1m6", "conv1", "p1", "Marcus Rivera", "Heading over now, about 8 minutes out.", "2:15 PM", false),
            ChatMessage("c1m7", "conv1", "system", "Assisto", "Provider is on the way", "2:20 PM", false, isSystem = true),
            ChatMessage("c1m8", "conv1", "seeker", "You", "Perfect, thank you!", "2:21 PM", true),
            ChatMessage("c1m9", "conv1", "p1", "Marcus Rivera", "On my way now!", "2:34 PM", false),
        ),
        "conv2" to listOf(
            ChatMessage("c2m0", "conv2", "system", "Assisto", "Provider accepted your request", "Yesterday", false, isSystem = true),
            ChatMessage("c2m1", "conv2", "seeker", "You", "Hi Sarah, I need help setting up my new router.", "Yesterday", true),
            ChatMessage("c2m2", "conv2", "p2", "Sarah Chen", "Happy to help! Which model is it?", "Yesterday", false),
            ChatMessage("c2m3", "conv2", "seeker", "You", "It's the one in this box.", "Yesterday", true, imageUrl = "https://picsum.photos/seed/router/400/300"),
            ChatMessage("c2m4", "conv2", "p2", "Sarah Chen", "Got it. I'll bring the right cables.", "Yesterday", false),
            ChatMessage("c2m5", "conv2", "seeker", "You", "Awesome, see you soon.", "Yesterday", true),
            ChatMessage("c2m6", "conv2", "p2", "Sarah Chen", "All connected and tested. Wi-Fi name is set.", "Yesterday", false),
            ChatMessage("c2m7", "conv2", "p2", "Sarah Chen", "Router is all set up. Enjoy!", "Yesterday", false),
        ),
    )

    val currentSeeker = seekers.first()

    val currentProvider = UserProfile(
        id = "u2",
        fullName = "Marcus Rivera",
        email = "marcus.rivera@email.com",
        phone = "(512) 555-0198",
        zipCode = "78745",
        city = DEFAULT_CITY,
        role = UserRole.PROVIDER,
        avatarUrl = "https://i.pravatar.cc/150?img=12",
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
        seekerAvatarUrl = "https://i.pravatar.cc/150?img=3",
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
