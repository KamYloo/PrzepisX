package com.example.przepisx.data.repository


import com.example.przepisx.data.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RecipeRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val recipesCollection = firestore.collection("recipes")

    suspend fun addRecipe(recipe: Recipe): Result<Unit> {
        return try {
            val newRecipeRef = recipesCollection.document()
            val recipeWithId = recipe.copy(id = newRecipeRef.id)
            newRecipeRef.set(recipeWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}