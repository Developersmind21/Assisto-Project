package com.example.assisto.ui.onboarding

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun SeekerProfileSetupScreen(
    viewModel: OnboardingViewModel,
    onComplete: () -> Unit,
) {
    val profile by viewModel.profile.collectAsState()

    AssistoScreen(
        title = "Set up your profile",
        subtitle = "We'll use this to match you with trusted providers in your area.",
        bottomBar = {
            AssistoButton(
                text = "Start requesting help",
                onClick = onComplete,
                enabled = viewModel.isSeekerProfileValid(),
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
            label = { Text("ZIP code") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
