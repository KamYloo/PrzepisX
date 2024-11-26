package com.example.przepisx.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Recipes : Screen("recipes", "Przepisy", Icons.Default.List)
    object Search : Screen("addRecipe", "Dodaj Przepis", Icons.Default.Search)
    object Shakomat : Screen("shakomat", "Shakomat", Icons.Default.Refresh)
    object Profile : Screen("profile", "Profil", Icons.Default.Person)
}
