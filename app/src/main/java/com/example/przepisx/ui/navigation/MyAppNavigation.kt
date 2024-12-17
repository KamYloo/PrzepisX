package com.example.przepisx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.przepisx.viewModel.AuthViewModel
import com.example.przepisx.data.model.strawberryCake
import com.example.przepisx.ui.screens.AddRecipePage
import com.example.przepisx.ui.screens.DessertsPage
import com.example.przepisx.ui.screens.HomePage
import com.example.przepisx.ui.screens.LoginPage
import com.example.przepisx.ui.screens.ProfilePage
import com.example.przepisx.ui.screens.RecipePage
import com.example.przepisx.ui.screens.RegisterPage
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

        composable("recipes") {
            LoggedInScaffold(navController) {
                DessertsPage(it, navController)
            }
        }

        composable("recipePage") {
            LoggedInScaffold(navController) {
                RecipePage(strawberryCake,it, navController)
            }
        }

        composable("addRecipe") {
            LoggedInScaffold(navController) {
                AddRecipePage(it, navController)
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