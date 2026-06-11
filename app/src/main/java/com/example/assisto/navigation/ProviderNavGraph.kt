package com.example.assisto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assisto.ui.provider.dashboard.ProviderDashboardScreen
import com.example.assisto.ui.provider.earnings.ProviderEarningsScreen
import com.example.assisto.ui.provider.jobs.ProviderJobsScreen
import com.example.assisto.ui.provider.profile.ProviderProfileEditScreen

data class ProviderNavItem(val route: String, val label: String, val icon: ImageVector)

private val providerNavItems = listOf(
    ProviderNavItem(ProviderTab.DASHBOARD, "Dashboard", Icons.Default.Dashboard),
    ProviderNavItem(ProviderTab.JOBS, "Jobs", Icons.Default.Work),
    ProviderNavItem(ProviderTab.EARNINGS, "Earnings", Icons.Default.AccountBalanceWallet),
    ProviderNavItem(ProviderTab.PROFILE, "Profile", Icons.Default.Person),
)

@Composable
fun ProviderMainScreen(
    onNavigate: (String) -> Unit,
    openIncomingRequest: Boolean = false,
) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            AssistoBottomNavBar(
                items = providerNavItems.map { BottomNavEntry(it.route, it.label, it.icon) },
                currentRoute = currentRoute,
                onSelect = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = ProviderTab.DASHBOARD,
            modifier = androidx.compose.ui.Modifier.padding(padding),
        ) {
            composable(ProviderTab.DASHBOARD) {
                ProviderDashboardScreen(
                    onEarningsClick = { navController.navigate(ProviderTab.EARNINGS) },
                    onActiveJobClick = { onNavigate(AssistoRoutes.activeJob(it)) },
                    onProfileClick = { navController.navigate(ProviderTab.PROFILE) },
                )
            }
            composable(ProviderTab.JOBS) {
                ProviderJobsScreen(onJobClick = { onNavigate(AssistoRoutes.activeJob("job1")) })
            }
            composable(ProviderTab.EARNINGS) { ProviderEarningsScreen() }
            composable(ProviderTab.PROFILE) {
                ProviderProfileEditScreen(onBack = {})
            }
        }
    }
}
