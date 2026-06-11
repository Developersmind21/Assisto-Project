package com.example.assisto.navigation

object AssistoRoutes {
    const val WELCOME = "welcome"
    const val ROLE_SELECTION = "role_selection"
    const val SEEKER_PROFILE_SETUP = "seeker_profile_setup"
    const val PROVIDER_PROFILE_SETUP = "provider_profile_setup"
    const val SEEKER_MAIN = "seeker_main"
    const val PROVIDER_MAIN = "provider_main"

    const val SERVICE_REQUEST = "service_request?category={category}"
    const val SERVICE_REQUEST_BASE = "service_request"
    fun serviceRequest(category: String? = null) =
        if (category != null) "service_request?category=$category" else SERVICE_REQUEST_BASE

    const val PROVIDER_MATCHING = "provider_matching/{requestId}"
    fun providerMatching(requestId: String) = "provider_matching/$requestId"

    const val PROVIDER_DETAIL = "provider_detail/{providerId}"
    fun providerDetail(providerId: String) = "provider_detail/$providerId"

    const val REQUEST_TRACKING = "request_tracking/{requestId}"
    fun requestTracking(requestId: String) = "request_tracking/$requestId"

    const val CHAT = "chat/{conversationId}"
    fun chat(conversationId: String) = "chat/$conversationId"

    const val JOB_COMPLETION = "job_completion/{requestId}"
    fun jobCompletion(requestId: String) = "job_completion/$requestId"

    const val RATING = "rating/{requestId}"
    fun rating(requestId: String) = "rating/$requestId"

    const val ACTIVE_JOB = "active_job/{jobId}"
    fun activeJob(jobId: String) = "active_job/$jobId"

    const val PROVIDER_PROFILE_EDIT = "provider_profile_edit"
    const val SOS_CONFIRMATION = "sos_confirmation"
}

object SeekerTab {
    const val HOME = "seeker_tab_home"
    const val REQUESTS = "seeker_tab_requests"
    const val MESSAGES = "seeker_tab_messages"
    const val PROFILE = "seeker_tab_profile"
}

object ProviderTab {
    const val DASHBOARD = "provider_tab_dashboard"
    const val JOBS = "provider_tab_jobs"
    const val EARNINGS = "provider_tab_earnings"
    const val PROFILE = "provider_tab_profile"
}
