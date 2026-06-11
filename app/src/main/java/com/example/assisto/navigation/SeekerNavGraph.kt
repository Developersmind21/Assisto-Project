package com.example.assisto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.rememberNavController
import com.example.assisto.ui.seeker.home.SeekerHomeScreen
import com.example.assisto.ui.seeker.tabs.SeekerMessagesScreen
import com.example.assisto.ui.seeker.tabs.SeekerProfileScreen
import com.example.assisto.ui.seeker.tabs.SeekerRequestsScreen

data class SeekerNavItem(val route: String, val label: String, val icon: ImageVector)

private val seekerNavItems = listOf(
    SeekerNavItem(SeekerTab.HOME, "Home", Icons.Default.Home),
    SeekerNavItem(SeekerTab.REQUESTS, "Requests", Icons.AutoMirrored.Filled.List),
    SeekerNavItem(SeekerTab.MESSAGES, "Messages", Icons.AutoMirrored.Filled.Chat),
    SeekerNavItem(SeekerTab.PROFILE, "Profile", Icons.Default.Person),
)

@Composable
fun SeekerMainScreen(
    onNavigate: (String) -> Unit,
) {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            AssistoBottomNavBar(
                items = seekerNavItems.map { BottomNavEntry(it.route, it.label, it.icon) },
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
            startDestination = SeekerTab.HOME,
            modifier = androidx.compose.ui.Modifier.padding(padding),
        ) {
            composable(SeekerTab.HOME) {
                SeekerHomeScreen(
                    onCategoryClick = { onNavigate(AssistoRoutes.serviceRequest(it.name)) },
                    onQuickRequest = { onNavigate(AssistoRoutes.SERVICE_REQUEST_BASE) },
                    onProviderClick = { onNavigate(AssistoRoutes.providerDetail(it)) },
                    onRequestClick = { onNavigate(AssistoRoutes.requestTracking(it)) },
                )
            }
            composable(SeekerTab.REQUESTS) {
                SeekerRequestsScreen(onRequestClick = { onNavigate(AssistoRoutes.requestTracking(it)) })
            }
            composable(SeekerTab.MESSAGES) {
                SeekerMessagesScreen(onChatClick = { onNavigate(AssistoRoutes.chat(it)) })
            }
            composable(SeekerTab.PROFILE) { SeekerProfileScreen() }
        }
    }
}
