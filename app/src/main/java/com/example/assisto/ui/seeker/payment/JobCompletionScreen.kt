package com.example.assisto.ui.seeker.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoButton
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.LoadingShimmerList

@Composable
fun JobCompletionScreen(
    onBack: () -> Unit,
    onPaymentSuccess: (String) -> Unit,
    viewModel: JobCompletionViewModel = assistoViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    androidx.compose.material3.Scaffold(
        topBar = { AssistoTopBar("Payment", onBackClick = onBack) },
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            when (val summary = state.summary) {
                is UiState.Loading -> LoadingShimmerList(1)
                is UiState.Success -> {
                    val s = summary.data
                    AssistoCard {
                        Text("Service Summary", style = MaterialTheme.typography.titleMedium)
                        Text(s.serviceType)
                        Text("Provider: ${s.providerName}")
                        Text("${s.duration} · ${s.date}")
                    }
                    Spacer(Modifier.height(16.dp))
                    AssistoCard {
                        Text("Cost Breakdown", style = MaterialTheme.typography.titleMedium)
                        Text("Base fee: $${"%.2f".format(s.baseFee)}")
                        Text("Platform fee: $${"%.2f".format(s.platformFee)}")
                        Text("Total: $${"%.2f".format(s.total)}", style = MaterialTheme.typography.titleLarge)
                    }
                }
                is UiState.Error -> Text(summary.message)
            }
            Spacer(Modifier.height(16.dp))
            Text("Payment method", style = MaterialTheme.typography.titleMedium)
            PaymentMethod.entries.forEach { method ->
                androidx.compose.foundation.layout.Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    RadioButton(selected = state.selectedMethod == method, onClick = { viewModel.selectMethod(method) })
                    Text(method.label)
                }
            }
            Spacer(Modifier.height(24.dp))
            AssistoButton(
                text = "Confirm & Pay",
                onClick = { viewModel.confirmPayment { onPaymentSuccess(viewModel.requestId) } },
                loading = state.isProcessing,
            )
        }
    }
}
