package com.example.przepisx.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.przepisx.ui.components.AddRecipeForm

@Composable
fun AddRecipePage(modifier: Modifier = Modifier, navController: NavController) {
    AddRecipeForm(
        onSave = { title, category, preparationTime, calories, description, ingredients, steps ->
            println("Zapisano przepis: $title, $category, $preparationTime min, $calories kcal")
            navController.popBackStack()
        }
    )
}
