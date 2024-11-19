package com.example.przepisx.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.przepisx.ui.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination?.route

        val screens = listOf(
            Screen.Recipes,
            Screen.Search,
            Screen.Shakomat,
            Screen.Profile
        )

        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
