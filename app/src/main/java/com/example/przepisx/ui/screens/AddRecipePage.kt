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
import com.example.przepisx.data.model.Recipe
import com.example.przepisx.ui.components.AddRecipeForm
import com.example.przepisx.viewModel.AddRecipeState
import com.example.przepisx.viewModel.RecipeViewModel

@Composable
fun AddRecipePage(modifier: Modifier = Modifier, navController: NavController) {

    val recipeViewModel: RecipeViewModel = viewModel()
    val addRecipeState by recipeViewModel.addRecipeState.collectAsState()

    AddRecipeForm(
        onSave = { title, category, cookingTime, energy, description, ingredients, steps ->
            recipeViewModel.addRecipe(
                Recipe(
                    title = title,
                    category = category,
                    cookingTime = cookingTime,
                    energy = energy,
                    description = description,
                    ingredients = ingredients,
                    steps = steps
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
