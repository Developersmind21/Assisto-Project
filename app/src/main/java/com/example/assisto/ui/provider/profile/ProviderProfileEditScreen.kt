package com.example.assisto.ui.provider.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.assisto.ui.components.AssistoBadge
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoTextField
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.BadgeType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProviderProfileEditScreen(onBack: () -> Unit, viewModel: ProviderProfileEditViewModel = assistoViewModel()) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    var skillInput by remember { mutableStateOf("") }

    Scaffold(topBar = { AssistoTopBar("Edit Profile", onBackClick = onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(profile?.avatarUrl, null, Modifier.size(96.dp).clip(RoundedCornerShape(48.dp)), contentScale = ContentScale.Crop)
            Text("Tap to change photo", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(16.dp))
            AssistoTextField(profile?.fullName ?: "", viewModel::updateName, "Full name")
            Spacer(Modifier.height(8.dp))
            AssistoTextField(profile?.bio ?: "", viewModel::updateBio, "Bio", singleLine = false, minLines = 3)
            Spacer(Modifier.height(8.dp))
            AssistoTextField(profile?.phone ?: "", {}, "Phone", enabled = false)
            Spacer(Modifier.height(12.dp))
            Text("Skills", style = MaterialTheme.typography.titleMedium)
            FlowRow(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                profile?.skills?.forEach { skill ->
                    FilterChip(selected = true, onClick = { viewModel.removeSkill(skill) }, label = { Text(skill) })
                }
            }
            AssistoTextField(skillInput, { skillInput = it }, "Add skill")
            AssistoButton("Add Skill", { if (skillInput.isNotBlank()) { viewModel.addSkill(skillInput); skillInput = "" } }, fillMaxWidth = false)
            Spacer(Modifier.height(16.dp))
            if (profile?.kycVerified == true) {
                AssistoBadge(BadgeType.Verified, "ID Verified")
            } else {
                AssistoButton("Complete Verification", {}, variant = com.example.assisto.ui.components.AssistoButtonVariant.Secondary)
            }
            Spacer(Modifier.height(24.dp))
            AssistoButton("Save Profile", { viewModel.save(); onBack() })
        }
    }
}
