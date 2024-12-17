package com.example.przepisx.data.repository


import com.example.przepisx.data.model.Dessert
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RecipeRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val recipesCollection = firestore.collection("recipes")

    suspend fun addRecipe(dessert: Dessert): Result<Unit> {
        return try {
            val newRecipeRef = recipesCollection.document()
            val recipeWithId = dessert.copy(id = newRecipeRef.id)
            newRecipeRef.set(recipeWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}