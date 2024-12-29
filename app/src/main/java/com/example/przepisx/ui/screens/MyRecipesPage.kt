package com.example.przepisx.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.przepisx.ui.components.DessertList
import com.example.przepisx.viewModel.UserRecipesState
import com.example.przepisx.viewModel.UserRecipesViewModel

@Composable
fun MyRecipesPage(modifier: Modifier = Modifier, navController: NavController) {
    val userRecipesViewModel: UserRecipesViewModel = viewModel()
    val userRecipesState by userRecipesViewModel.userRecipesState.collectAsState()

    LaunchedEffect(Unit) {
        userRecipesViewModel.loadUserRecipes()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF2F2F2F),
                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Moje Przepisy",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                when (userRecipesState) {
                    is UserRecipesState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 24.dp)
                        )
                    }
                    is UserRecipesState.Success -> {
                        val recipes = (userRecipesState as UserRecipesState.Success).recipes
                        if (recipes.isNotEmpty()) {
                            DessertList(desserts = recipes, navController = navController)
                        } else {
                            Text(
                                text = "Brak przepisów. Dodaj nowe, aby zacząć!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    is UserRecipesState.Error -> {
                        Text(
                            text = "Błąd: ${(userRecipesState as UserRecipesState.Error).message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> Unit
                }
            }
        }
    }
}
