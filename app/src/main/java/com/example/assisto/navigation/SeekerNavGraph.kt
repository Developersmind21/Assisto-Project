package com.example.assisto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
    SeekerNavItem(SeekerTab.REQUESTS, "Requests", Icons.Default.List),
    SeekerNavItem(SeekerTab.MESSAGES, "Messages", Icons.Default.Chat),
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
            NavigationBar {
                seekerNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                    )
                }
            }
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
