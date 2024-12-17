package com.example.przepisx.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.przepisx.network.RecipeApi
import com.example.przepisx.data.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShakomatViewModel : ViewModel() {
    private val recipeApi = RecipeApi.create()

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    fun fetchRandomRecipe() {
        viewModelScope.launch {
            try {
                val response = recipeApi.getRandomRecipe()
                _recipe.value = response.recipes.firstOrNull()
            } catch (e: Exception) {
                Log.e("ShakomatViewModel", "Error fetching random recipe", e)
            }
        }
    }
}