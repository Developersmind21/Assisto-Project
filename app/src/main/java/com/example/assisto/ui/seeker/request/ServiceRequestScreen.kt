package com.example.assisto.ui.seeker.request

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assisto.data.model.BudgetRange
import com.example.assisto.data.model.TimingOption
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoButtonVariant
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoMapPlaceholder
import com.example.assisto.ui.components.AssistoTextField
import com.example.assisto.ui.components.AssistoTopBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceRequestScreen(
    initialCategory: String?,
    onBack: () -> Unit,
    onRequestCreated: (String) -> Unit,
    viewModel: ServiceRequestViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(initialCategory) { viewModel.initCategory(initialCategory) }

    Scaffold(
        topBar = { AssistoTopBar("New Request", onBackClick = onBack) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text("Step ${state.step} of 3", style = MaterialTheme.typography.labelLarge)
            LinearProgressIndicator(
                progress = { state.step / 3f },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )
            when (state.step) {
                1 -> {
                    Text("Select Category & Describe", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(12.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        viewModel.subCategories().forEach { sub ->
                            FilterChip(
                                selected = state.subCategory == sub.name,
                                onClick = { viewModel.setSubCategory(sub.name) },
                                label = { Text(sub.name) },
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    AssistoTextField(
                        value = state.description,
                        onValueChange = viewModel::setDescription,
                        label = "Describe the issue",
                        singleLine = false,
                        minLines = 4,
                        supportingText = "${state.description.length}/300",
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Photos (optional)", style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        repeat(state.photoCount) {
                            Box(Modifier.size(72.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant))
                        }
                        if (state.photoCount < 3) {
                            Box(
                                Modifier.size(72.dp).clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { viewModel.addPhoto() },
                                contentAlignment = Alignment.Center,
                            ) {
                                androidx.compose.material3.Icon(Icons.Default.Add, null)
                            }
                        }
                    }
                }
                2 -> {
                    Text("Location", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(12.dp))
                    Box(Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp))) {
                        AssistoMapPlaceholder(address = state.address)
                    }
                    Spacer(Modifier.height(12.dp))
                    AssistoButton(
                        text = "Use my current location",
                        onClick = viewModel::useCurrentLocation,
                        variant = AssistoButtonVariant.Ghost,
                        fillMaxWidth = true,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(state.address, style = MaterialTheme.typography.bodyMedium)
                }
                3 -> {
                    Text("Timing & Budget", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(12.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TimingOption.entries.forEach { opt ->
                            FilterChip(selected = state.timing == opt, onClick = { viewModel.setTiming(opt) }, label = { Text(opt.label) })
                        }
                    }
                    if (state.timing == TimingOption.SCHEDULED) {
                        AssistoTextField(state.scheduledDateTime, viewModel::setScheduled, "Date & time", placeholder = "Mar 8, 2026 2:00 PM")
                    }
                    Spacer(Modifier.height(12.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BudgetRange.entries.forEach { b ->
                            FilterChip(selected = state.budget == b, onClick = { viewModel.setBudget(b) }, label = { Text(b.label) })
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    AssistoCard {
                        Text("Summary", style = MaterialTheme.typography.titleMedium)
                        Text("${state.category.displayName} · ${state.subCategory}")
                        Text(state.description.take(80) + if (state.description.length > 80) "…" else "")
                        Text(state.address, style = MaterialTheme.typography.bodySmall)
                        Text("${state.timing.label} · ${state.budget.label}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (state.step > 1) {
                    AssistoButton("Back", viewModel::prevStep, modifier = Modifier.weight(1f), variant = AssistoButtonVariant.Ghost)
                }
                if (state.step < 3) {
                    AssistoButton("Continue", viewModel::nextStep, modifier = Modifier.weight(1f), enabled = state.subCategory.isNotBlank() && (state.step != 1 || state.description.isNotBlank()))
                } else {
                    AssistoButton("Find Providers", { viewModel.submit(onRequestCreated) }, modifier = Modifier.weight(1f), loading = state.isSubmitting)
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
