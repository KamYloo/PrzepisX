package com.example.przepisx.data.model

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val title: String,
    val summary: String,
    val image: String,
    val instructions: String,
    val extendedIngredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val amount: Double,
    val unit: String
)