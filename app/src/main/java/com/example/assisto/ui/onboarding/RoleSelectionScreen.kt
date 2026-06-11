package com.example.assisto.ui.onboarding

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assisto.data.model.UserRole
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoScreen
import com.example.assisto.ui.components.RoleSelectionCard

@Composable
fun RoleSelectionScreen(
    viewModel: OnboardingViewModel,
    onContinue: (UserRole) -> Unit,
) {
    val profile by viewModel.profile.collectAsState()
    val selectedRole = profile.role

    AssistoScreen(
        title = "How will you use Assisto?",
        subtitle = "Choose your role to personalize your experience. You can switch later in settings.",
        bottomBar = {
            AssistoButton(
                text = "Continue",
                onClick = { selectedRole?.let(onContinue) },
                enabled = selectedRole != null,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            )
        },
    ) {
        RoleSelectionCard(
            title = "I need help",
            description = "Request services from verified providers near you — fast, transparent, and secure.",
            selected = selectedRole == UserRole.SEEKER,
            onClick = { viewModel.selectRole(UserRole.SEEKER) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoleSelectionCard(
            title = "I provide services",
            description = "Earn on your schedule. Get real-time job alerts, fair payouts, and build your reputation.",
            selected = selectedRole == UserRole.PROVIDER,
            onClick = { viewModel.selectRole(UserRole.PROVIDER) },
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
