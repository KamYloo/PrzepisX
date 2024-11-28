package com.example.przepisx.data.model

import com.example.przepisx.R
import java.util.UUID


data class Recipe(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val category: String = "",
    val cookingTime: String = "",
    val energy: String = "",
    val description: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<String> = emptyList()
)

val strawberryCake = Recipe(
    title = "Ciasto Truskawkowe",
    category = "Owocowe",
    cookingTime = "50 min",
    energy = "620 kcal",
    description = "Ten deser jest bardzo smaczny i nietrudny w przygotowaniu. Możesz również zastąpić truskawki dowolnymi innymi jagodami, które lubisz.",
    ingredients = listOf(
        Ingredient(R.drawable.flour, "Mąka", "450 g"),
        Ingredient(R.drawable.eggs, "Jajka", "4"),
        Ingredient(R.drawable.juice, "Sok z cytryny", "150 g"),
        Ingredient(R.drawable.strawberry, "Truskawki", "200 g"),
        Ingredient(R.drawable.suggar, "Cukier", "1 szklanka"),
        Ingredient(R.drawable.mind, "Mięta", "20 g"),
        Ingredient(R.drawable.vanilla, "Cukier Waniliowy", "1/2 łyżeczki"),
    ),
    steps = listOf(
        "Wymieszaj mąkę i cukier.",
        "Dodaj jajka i sok z cytryny, dokładnie wymieszaj.",
        "Dodaj pokrojone truskawki i delikatnie wymieszaj.",
        "Piecz w temperaturze 180°C przez około 50 minut.",
        "Podawaj z miętą na wierzchu."
    )
)
