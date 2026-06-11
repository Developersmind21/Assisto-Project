package com.example.assisto.navigation

/**
 * Typed navigation routes for the whole app. Parameterized routes expose a
 * [build] helper that fills in the path argument.
 */
sealed class Routes(val route: String) {
    data object RoleSelection : Routes("role_selection")
    data object SeekerSetup : Routes("seeker_setup")
    data object ProviderSetup : Routes("provider_setup")

    data object SeekerMain : Routes("seeker_main")
    data object SeekerHome : Routes("seeker_home")
    data object MyRequests : Routes("my_requests")
    data object SeekerMessages : Routes("seeker_messages")
    data object SeekerProfile : Routes("seeker_profile")

    data object CreateRequest : Routes("create_request") {
        const val ARG = "category"
        val routeWithArg = "create_request?category={category}"
        fun build(category: String? = null) =
            if (category != null) "create_request?category=$category" else route
    }

    data object ProviderMatching : Routes("provider_matching/{requestId}") {
        fun build(id: String) = "provider_matching/$id"
    }
    data object ProviderProfile : Routes("provider_profile/{providerId}") {
        fun build(id: String) = "provider_profile/$id"
    }
    data object RequestTracking : Routes("request_tracking/{requestId}") {
        fun build(id: String) = "request_tracking/$id"
    }
    data object Chat : Routes("chat/{threadId}") {
        fun build(id: String) = "chat/$id"
    }
    data object Payment : Routes("payment/{requestId}") {
        fun build(id: String) = "payment/$id"
    }
    data object RateProvider : Routes("rate_provider/{requestId}") {
        fun build(id: String) = "rate_provider/$id"
    }

    data object ProviderMain : Routes("provider_main")
    data object ProviderHome : Routes("provider_home")
    data object ProviderJobs : Routes("provider_jobs")
    data object ProviderEarnings : Routes("provider_earnings")
    data object ProviderProfileEdit : Routes("provider_profile_edit")
    data object ProviderActiveJob : Routes("provider_active_job/{requestId}") {
        fun build(id: String) = "provider_active_job/$id"
    }

    data object SosConfirmation : Routes("sos_confirmation")
}
