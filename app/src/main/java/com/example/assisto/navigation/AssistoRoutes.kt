package com.example.assisto.navigation

/**
 * Thin helper surface over [Routes]. All values resolve to the canonical
 * spec route strings defined in [Routes]; builders forward to the typed
 * [Routes] builders.
 */
object AssistoRoutes {
    const val WELCOME = "welcome"
    val ROLE_SELECTION = Routes.RoleSelection.route
    val SEEKER_PROFILE_SETUP = Routes.SeekerSetup.route
    val PROVIDER_PROFILE_SETUP = Routes.ProviderSetup.route
    val SEEKER_MAIN = Routes.SeekerMain.route
    val PROVIDER_MAIN = Routes.ProviderMain.route

    val SERVICE_REQUEST = Routes.CreateRequest.routeWithArg
    val SERVICE_REQUEST_BASE = Routes.CreateRequest.route
    fun serviceRequest(category: String? = null) = Routes.CreateRequest.build(category)

    val PROVIDER_MATCHING = Routes.ProviderMatching.route
    fun providerMatching(requestId: String) = Routes.ProviderMatching.build(requestId)

    val PROVIDER_DETAIL = Routes.ProviderProfile.route
    fun providerDetail(providerId: String) = Routes.ProviderProfile.build(providerId)

    val REQUEST_TRACKING = Routes.RequestTracking.route
    fun requestTracking(requestId: String) = Routes.RequestTracking.build(requestId)

    val CHAT = Routes.Chat.route
    fun chat(threadId: String) = Routes.Chat.build(threadId)

    val JOB_COMPLETION = Routes.Payment.route
    fun jobCompletion(requestId: String) = Routes.Payment.build(requestId)

    val RATING = Routes.RateProvider.route
    fun rating(requestId: String) = Routes.RateProvider.build(requestId)

    val ACTIVE_JOB = Routes.ProviderActiveJob.route
    fun activeJob(requestId: String) = Routes.ProviderActiveJob.build(requestId)

    val PROVIDER_PROFILE_EDIT = Routes.ProviderProfileEdit.route
    val SOS_CONFIRMATION = Routes.SosConfirmation.route
}

object SeekerTab {
    val HOME = Routes.SeekerHome.route
    val REQUESTS = Routes.MyRequests.route
    val MESSAGES = Routes.SeekerMessages.route
    val PROFILE = Routes.SeekerProfile.route
}

object ProviderTab {
    val DASHBOARD = Routes.ProviderHome.route
    val JOBS = Routes.ProviderJobs.route
    val EARNINGS = Routes.ProviderEarnings.route
    val PROFILE = Routes.ProviderProfileEdit.route
}
