package com.example.przepisx.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.przepisx.data.model.Dessert
import com.example.przepisx.ui.components.AddRecipeForm
import com.example.przepisx.viewModel.AddRecipeState
import com.example.przepisx.viewModel.DessertViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddRecipePage(modifier: Modifier = Modifier, navController: NavController) {

    val dessertViewModel: DessertViewModel = viewModel()
    val addRecipeState by dessertViewModel.addRecipeState.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    AddRecipeForm(
        onSave = { title, category, cookingTime, energy, description, ingredients, steps ->
            dessertViewModel.addRecipe(
                Dessert(
                    title = title,
                    category = category,
                    cookingTime = cookingTime,
                    energy = energy,
                    description = description,
                    dessertIngredients = ingredients,
                    steps = steps,
                    authorId = userId
                )
            )
        }
    )

    when (addRecipeState) {
        AddRecipeState.Loading -> CircularProgressIndicator()
        AddRecipeState.Success -> {
            LaunchedEffect(Unit) {
                navController.popBackStack()
            }
        }
        is AddRecipeState.Error -> {
            Text(
                text = "Error: ${(addRecipeState as AddRecipeState.Error).message}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> Unit
    }
}