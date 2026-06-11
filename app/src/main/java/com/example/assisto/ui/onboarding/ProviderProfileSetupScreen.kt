package com.example.assisto.ui.onboarding

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoScreen

private val pilotCategories = listOf(
    "Home Services",
    "Tech Support",
    "Auto & Mechanical",
    "Education / Tutoring",
    "Personal Assistance",
)

@Composable
fun ProviderProfileSetupScreen(
    viewModel: OnboardingViewModel,
    onComplete: () -> Unit,
) {
    val profile by viewModel.profile.collectAsState()

    AssistoScreen(
        title = "Provider profile",
        subtitle = "Complete your profile to start receiving job alerts. Identity verification (KYC) is required before your first payout.",
        bottomBar = {
            AssistoButton(
                text = "Continue to verification",
                onClick = onComplete,
                enabled = viewModel.isProviderProfileValid(),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            )
        },
    ) {
        OutlinedTextField(
            value = profile.fullName,
            onValueChange = viewModel::updateFullName,
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = profile.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = profile.phone,
            onValueChange = viewModel::updatePhone,
            label = { Text("Phone number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = profile.zipCode,
            onValueChange = viewModel::updateZipCode,
            label = { Text("Service area ZIP code") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = profile.primaryCategory,
            onValueChange = viewModel::updatePrimaryCategory,
            label = { Text("Primary service category") },
            placeholder = { Text(pilotCategories.first()) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = profile.bio,
            onValueChange = viewModel::updateBio,
            label = { Text("Short bio & skills") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            minLines = 3,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            ),
        ) {
            Text(
                text = "Next step: Government ID upload and selfie verification. Providers must maintain a 3.5+ rating to stay active.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}
