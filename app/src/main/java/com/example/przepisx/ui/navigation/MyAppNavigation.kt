package com.example.przepisx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.przepisx.AuthViewModel
import com.example.przepisx.ui.screens.HomePage
import com.example.przepisx.ui.screens.LoginPage
import com.example.przepisx.ui.screens.ProfilePage
import com.example.przepisx.ui.screens.RecipesPage
import com.example.przepisx.ui.screens.RegisterPage
import com.example.przepisx.ui.screens.SearchPage
import com.example.przepisx.ui.screens.ShakomatPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("register") {
            RegisterPage(modifier, navController, authViewModel)
        }
        composable("home") {
            HomePage(modifier, navController)
        }
        // Ekrany po zalogowaniu
        composable("recipes") {
            LoggedInScaffold(navController) {
                RecipesPage(it, navController)
            }
        }
        composable("search") {
            LoggedInScaffold(navController) {
                SearchPage(it, navController)
            }
        }
        composable("shakomat") {
            LoggedInScaffold(navController) {
                ShakomatPage(it, navController)
            }
        }
        composable("profile") {
            LoggedInScaffold(navController) {
                ProfilePage(it, navController, authViewModel)
            }
        }
    })
}