package com.example.przepisx.data.repository

import com.example.przepisx.data.model.Dessert
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DessertRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val dessertsCollection = firestore.collection("desserts")

    suspend fun addDessert(dessert: Dessert): Result<Unit> {
        return try {
            val newRecipeRef = dessertsCollection.document()
            val recipeWithId = dessert.copy(id = newRecipeRef.id)
            newRecipeRef.set(recipeWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDesserts(): Result<List<Dessert>> {
        return try {
            val desserts = dessertsCollection.get().await().toObjects(Dessert::class.java)
            Result.success(desserts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDessertById(recipeId: String): Result<Dessert?> {
        return try {
            val document = dessertsCollection.document(recipeId).get().await()
            if (document.exists()) {
                val dessert = document.toObject(Dessert::class.java)
                Result.success(dessert)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteDessert(dessertId: String): Result<Unit> {
        return try {
            dessertsCollection.document(dessertId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}