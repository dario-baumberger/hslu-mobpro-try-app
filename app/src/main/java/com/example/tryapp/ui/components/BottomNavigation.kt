package com.example.tryapp.ui.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.tryapp.TryApplicationScreens
import com.example.tryapp.business.bands.BandsViewModel
import com.example.tryapp.ui.components.navigation.BottomNavigationItem

@Composable
fun BottomNavigation(
    navController: NavHostController,
) {
    val activity = LocalActivity.current as ComponentActivity
    val bandsViewModel: BandsViewModel = hiltViewModel(activity)

    val bandCount by bandsViewModel.bandsFlow.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    val currentDestination = navBackStackEntry?.destination

    val navigationItems = listOf(
        BottomNavigationItem(
            route = TryApplicationScreens.Home.route,
            title = TryApplicationScreens.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            route = TryApplicationScreens.Bands.route,
            title = TryApplicationScreens.Bands.route,
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            hasNews = bandCount.isNotEmpty(),
            badgeCount = bandCount.size.takeIf { it > 0 }
        ),
        BottomNavigationItem(
            route = TryApplicationScreens.Users.route,
            title = TryApplicationScreens.Users.route,
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face,
            hasNews = false
        )
    )

    NavigationBar {
        navigationItems.forEach { item ->
            val selected = currentDestination.isRouteSelected(item.route)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = {
                    if (item.badgeCount != null || item.hasNews) {
                        BadgedBox(
                            badge = {
                                when {
                                    item.badgeCount != null -> {
                                        Badge { Text(item.badgeCount.toString()) }
                                    }

                                    item.hasNews -> {
                                        Badge()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                },
                label = { Text(item.title) }
            )
        }
    }
}

private fun NavDestination?.isRouteSelected(route: String): Boolean {
    return this?.hierarchy?.any { it.route == route } == true
}
