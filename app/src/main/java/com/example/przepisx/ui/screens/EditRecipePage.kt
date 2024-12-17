package com.example.przepisx.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.przepisx.ui.components.EditRecipeForm
import com.example.przepisx.viewModel.DessertDetailsState
import com.example.przepisx.viewModel.DessertViewModel
import kotlinx.coroutines.launch


@Composable
fun EditRecipePage(recipeId: String, navController: NavController) {
    val dessertViewModel: DessertViewModel = viewModel()
    val dessertState by dessertViewModel.dessertDetailsState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(recipeId) {
        dessertViewModel.loadDessertById(recipeId)
    }

    when (dessertState) {
        is DessertDetailsState.Success -> {
            val dessert = (dessertState as DessertDetailsState.Success).dessert
            EditRecipeForm(dessert) { updatedDessert ->
                coroutineScope.launch {
                    dessertViewModel.updateDessert(recipeId, updatedDessert)
                    navController.popBackStack()
                }
            }
        }
        is DessertDetailsState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        is DessertDetailsState.Error -> {
            Text("Błąd: ${(dessertState as DessertDetailsState.Error).message}")
        }
        else -> Unit
    }
}