package com.example.assisto.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.assisto.ui.onboarding.OnboardingViewModel
import com.example.assisto.ui.provider.activejob.ActiveJobViewModel
import com.example.assisto.ui.provider.dashboard.ProviderDashboardViewModel
import com.example.assisto.ui.provider.earnings.ProviderEarningsViewModel
import com.example.assisto.ui.provider.jobs.ProviderJobsViewModel
import com.example.assisto.ui.provider.profile.ProviderProfileEditViewModel
import com.example.assisto.ui.seeker.chat.ChatViewModel
import com.example.assisto.ui.seeker.home.SeekerHomeViewModel
import com.example.assisto.ui.seeker.matching.ProviderMatchingViewModel
import com.example.assisto.ui.seeker.payment.JobCompletionViewModel
import com.example.assisto.ui.seeker.providerdetail.ProviderDetailViewModel
import com.example.assisto.ui.seeker.rating.RatingViewModel
import com.example.assisto.ui.seeker.request.ServiceRequestViewModel
import com.example.assisto.ui.seeker.tabs.SeekerMessagesViewModel
import com.example.assisto.ui.seeker.tabs.SeekerProfileTabViewModel
import com.example.assisto.ui.seeker.tabs.SeekerRequestsViewModel
import com.example.assisto.ui.seeker.tracking.RequestTrackingViewModel

class AssistoViewModelFactory(
    private val application: Application,
    private val container: AppContainer,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()
        return createInternal(modelClass, savedStateHandle) as T
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return createInternal(modelClass, SavedStateHandle()) as T
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewModel> createInternal(modelClass: Class<T>, savedStateHandle: SavedStateHandle): T {
        return when {
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) ->
                OnboardingViewModel(container.mockUserRepository) as T
            modelClass.isAssignableFrom(SeekerHomeViewModel::class.java) ->
                SeekerHomeViewModel(container.userRepository, container.requestRepository, container.providerRepository) as T
            modelClass.isAssignableFrom(ServiceRequestViewModel::class.java) ->
                ServiceRequestViewModel(container.requestRepository) as T
            modelClass.isAssignableFrom(ProviderMatchingViewModel::class.java) ->
                ProviderMatchingViewModel(container.providerRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(ProviderDetailViewModel::class.java) ->
                ProviderDetailViewModel(container.providerRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(RequestTrackingViewModel::class.java) ->
                RequestTrackingViewModel(container.requestRepository, container.providerRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(ChatViewModel::class.java) ->
                ChatViewModel(container.requestRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(JobCompletionViewModel::class.java) ->
                JobCompletionViewModel(container.requestRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(RatingViewModel::class.java) ->
                RatingViewModel(container.requestRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(SeekerRequestsViewModel::class.java) ->
                SeekerRequestsViewModel(container.requestRepository) as T
            modelClass.isAssignableFrom(SeekerMessagesViewModel::class.java) ->
                SeekerMessagesViewModel(container.requestRepository) as T
            modelClass.isAssignableFrom(SeekerProfileTabViewModel::class.java) ->
                SeekerProfileTabViewModel(container.userRepository) as T
            modelClass.isAssignableFrom(ProviderDashboardViewModel::class.java) ->
                ProviderDashboardViewModel(container.userRepository, container.requestRepository, container.mockRequestRepository) as T
            modelClass.isAssignableFrom(ActiveJobViewModel::class.java) ->
                ActiveJobViewModel(container.requestRepository, savedStateHandle) as T
            modelClass.isAssignableFrom(ProviderEarningsViewModel::class.java) ->
                ProviderEarningsViewModel(container.userRepository) as T
            modelClass.isAssignableFrom(ProviderProfileEditViewModel::class.java) ->
                ProviderProfileEditViewModel(container.userRepository) as T
            modelClass.isAssignableFrom(ProviderJobsViewModel::class.java) ->
                ProviderJobsViewModel(container.requestRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
