package com.example.assisto.ui.provider.earnings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assisto.di.assistoViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assisto.data.repository.EarningsPeriod
import com.example.assisto.ui.common.UiState
import com.example.assisto.ui.components.AssistoCard
import com.example.assisto.ui.components.AssistoTopBar
import com.example.assisto.ui.components.LoadingShimmerList
import com.example.assisto.ui.components.StatusChip
import com.example.assisto.ui.theme.SuccessGreen

@Composable
fun ProviderEarningsScreen(onBack: (() -> Unit)? = null, viewModel: ProviderEarningsViewModel = assistoViewModel()) {
    val period by viewModel.period.collectAsStateWithLifecycle()
    val data by viewModel.data.collectAsStateWithLifecycle()
    val tabs = EarningsPeriod.entries

    Scaffold(topBar = { AssistoTopBar("Earnings", onBackClick = onBack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = tabs.indexOf(period)) {
                tabs.forEachIndexed { i, p ->
                    Tab(
                        selected = period == p,
                        onClick = { viewModel.setPeriod(p) },
                        text = { Text(when (p) { EarningsPeriod.TODAY -> "Today"; EarningsPeriod.THIS_WEEK -> "Week"; EarningsPeriod.THIS_MONTH -> "Month" }) },
                    )
                }
            }
            when (val d = data) {
                is UiState.Loading -> LoadingShimmerList(3)
                is UiState.Success -> {
                    Column(Modifier.padding(20.dp)) {
                        Text("$${"%.2f".format(d.data.total)}", style = MaterialTheme.typography.displaySmall)
                        Text("Next payout: $${d.data.nextPayoutAmount.toInt()} on ${d.data.nextPayoutDate}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(16.dp))
                        EarningsChart(d.data.chartValues)
                    }
                    LazyColumn(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(d.data.entries) { e ->
                            AssistoCard(elevation = 2) {
                                androidx.compose.foundation.layout.Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text(e.serviceType, style = MaterialTheme.typography.titleMedium)
                                        Text(e.date, style = MaterialTheme.typography.bodySmall)
                                    }
                                    Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                                        Text("$${e.amount.toInt()}", style = MaterialTheme.typography.titleMedium)
                                        StatusChip(e.status.name, SuccessGreen)
                                    }
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> Text(d.message, Modifier.padding(20.dp))
            }
        }
    }
}

@Composable
private fun EarningsChart(values: List<Float>) {
    val max = values.maxOrNull() ?: 1f
    Canvas(Modifier.fillMaxWidth().height(100.dp)) {
        val barWidth = size.width / (values.size * 2)
        values.forEachIndexed { i, v ->
            val h = (v / max) * size.height
            drawRoundRect(Color(0xFF2E75B6), Offset(i * barWidth * 2 + barWidth / 2, size.height - h), Size(barWidth, h), CornerRadius(6f))
        }
    }
}
