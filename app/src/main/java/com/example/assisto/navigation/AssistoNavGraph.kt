package com.example.assisto.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.assisto.di.assistoViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assisto.data.model.UserRole
import com.example.assisto.ui.common.SosConfirmationScreen
import com.example.assisto.ui.onboarding.OnboardingViewModel
import com.example.assisto.ui.onboarding.ProviderProfileSetupScreen
import com.example.assisto.ui.onboarding.RoleSelectionScreen
import com.example.assisto.ui.onboarding.SeekerProfileSetupScreen
import com.example.assisto.ui.provider.activejob.ActiveJobScreen
import com.example.assisto.ui.seeker.chat.ChatScreen
import com.example.assisto.ui.seeker.matching.ProviderMatchingScreen
import com.example.assisto.ui.seeker.payment.JobCompletionScreen
import com.example.assisto.ui.seeker.providerdetail.ProviderDetailScreen
import com.example.assisto.ui.seeker.rating.RatingScreen
import com.example.assisto.ui.seeker.request.ServiceRequestScreen
import com.example.assisto.ui.seeker.tracking.RequestTrackingScreen

@Composable
fun AssistoNavGraph(
    navController: NavHostController = rememberNavController(),
    onboardingViewModel: OnboardingViewModel = assistoViewModel(),
    openIncomingRequest: Boolean = false,
) {
    val profile by onboardingViewModel.profile.collectAsState()

    val tween = tween<Float>(280)
    NavHost(
        navController = navController,
        startDestination = Routes.RoleSelection.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn(tween) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut(tween) },
    ) {
        composable(AssistoRoutes.ROLE_SELECTION) {
            RoleSelectionScreen(
                viewModel = onboardingViewModel,
                onContinue = { role ->
                    val dest = when (role) {
                        UserRole.SEEKER -> AssistoRoutes.SEEKER_PROFILE_SETUP
                        UserRole.PROVIDER -> AssistoRoutes.PROVIDER_PROFILE_SETUP
                    }
                    navController.navigate(dest)
                },
            )
        }
        composable(AssistoRoutes.SEEKER_PROFILE_SETUP) {
            SeekerProfileSetupScreen(
                viewModel = onboardingViewModel,
                onComplete = {
                    onboardingViewModel.completeSeekerOnboarding()
                    navController.navigate(AssistoRoutes.SEEKER_MAIN) {
                        popUpTo(AssistoRoutes.ROLE_SELECTION) { inclusive = true }
                    }
                },
            )
        }
        composable(AssistoRoutes.PROVIDER_PROFILE_SETUP) {
            ProviderProfileSetupScreen(
                viewModel = onboardingViewModel,
                onComplete = {
                    onboardingViewModel.completeProviderOnboarding()
                    navController.navigate(AssistoRoutes.PROVIDER_MAIN) {
                        popUpTo(AssistoRoutes.ROLE_SELECTION) { inclusive = true }
                    }
                },
            )
        }
        composable(AssistoRoutes.SEEKER_MAIN) {
            SeekerMainScreen(onNavigate = { navController.navigate(it) })
        }
        composable(AssistoRoutes.PROVIDER_MAIN) {
            ProviderMainScreen(
                onNavigate = { navController.navigate(it) },
                openIncomingRequest = openIncomingRequest,
            )
        }

        composable(
            route = AssistoRoutes.SERVICE_REQUEST,
            arguments = listOf(navArgument("category") { type = NavType.StringType; nullable = true; defaultValue = null }),
        ) { entry ->
            ServiceRequestScreen(
                initialCategory = entry.arguments?.getString("category"),
                onBack = { navController.popBackStack() },
                onRequestCreated = { id -> navController.navigate(AssistoRoutes.providerMatching(id)) },
            )
        }
        composable(AssistoRoutes.SERVICE_REQUEST_BASE) {
            ServiceRequestScreen(
                initialCategory = null,
                onBack = { navController.popBackStack() },
                onRequestCreated = { id -> navController.navigate(AssistoRoutes.providerMatching(id)) },
            )
        }
        composable(
            route = AssistoRoutes.PROVIDER_MATCHING,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType }),
        ) {
            ProviderMatchingScreen(
                onBack = { navController.popBackStack() },
                onViewProfile = { navController.navigate(AssistoRoutes.providerDetail(it)) },
                onRequestProvider = { providerId ->
                    navController.navigate(AssistoRoutes.requestTracking("req1")) {
                        popUpTo(AssistoRoutes.SEEKER_MAIN) { inclusive = false }
                    }
                },
            )
        }
        composable(
            route = AssistoRoutes.PROVIDER_DETAIL,
            arguments = listOf(navArgument("providerId") { type = NavType.StringType }),
        ) {
            ProviderDetailScreen(
                onBack = { navController.popBackStack() },
                onRequest = { navController.navigate(AssistoRoutes.requestTracking("req1")) },
            )
        }
        composable(
            route = AssistoRoutes.REQUEST_TRACKING,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType }),
        ) {
            RequestTrackingScreen(
                onBack = { navController.popBackStack() },
                onChat = { navController.navigate(AssistoRoutes.chat(it)) },
                onSos = { navController.navigate(AssistoRoutes.SOS_CONFIRMATION) },
                onComplete = { id -> navController.navigate(AssistoRoutes.jobCompletion(id)) },
            )
        }
        composable(
            route = AssistoRoutes.CHAT,
            arguments = listOf(navArgument("threadId") { type = NavType.StringType }),
        ) {
            ChatScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = AssistoRoutes.JOB_COMPLETION,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType }),
        ) {
            JobCompletionScreen(
                onBack = { navController.popBackStack() },
                onPaymentSuccess = { id -> navController.navigate(AssistoRoutes.rating(id)) { popUpTo(AssistoRoutes.SEEKER_MAIN) } },
            )
        }
        composable(
            route = AssistoRoutes.RATING,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType }),
        ) {
            RatingScreen(
                onBack = { navController.popBackStack() },
                onSubmitted = { navController.navigate(AssistoRoutes.SEEKER_MAIN) { popUpTo(AssistoRoutes.SEEKER_MAIN) { inclusive = true } } },
            )
        }
        composable(
            route = AssistoRoutes.ACTIVE_JOB,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType }),
        ) {
            ActiveJobScreen(
                onBack = { navController.popBackStack() },
                onSos = { navController.navigate(AssistoRoutes.SOS_CONFIRMATION) },
                onChat = { navController.navigate(AssistoRoutes.chat("conv1")) },
            )
        }
        composable(AssistoRoutes.SOS_CONFIRMATION) {
            SosConfirmationScreen(onDone = { navController.popBackStack() })
        }
        composable(AssistoRoutes.PROVIDER_PROFILE_EDIT) {
            com.example.assisto.ui.provider.profile.ProviderProfileEditScreen(onBack = { navController.popBackStack() })
        }
    }
}
