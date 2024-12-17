package com.example.przepisx.network

import com.example.przepisx.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RecipeApi {
    @GET("recipes/random?apiKey=cfbc34cf6be5485cb2a6224e88c6ad4d")
    suspend fun getRandomRecipe(): RecipeResponse

    companion object {
        private const val BASE_URL = "https://api.spoonacular.com/"

        fun create(): RecipeApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApi::class.java)
        }
    }
}