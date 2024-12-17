package com.example.przepisx.data.model

import java.util.UUID

data class Dessert(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val category: String = "",
    val cookingTime: String = "",
    val energy: String = "",
    val description: String = "",
    val dessertIngredients: List<DessertIngredient> = emptyList(),
    val steps: List<String> = emptyList(),
    val authorId: String = ""
)

