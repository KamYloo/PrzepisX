package com.example.przepisx.data.model

import com.example.przepisx.R
import java.util.UUID


data class Dessert(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val category: String = "",
    val cookingTime: String = "",
    val energy: String = "",
    val description: String = "",
    val dessertIngredients: List<DessertIngredient> = emptyList(),
    val steps: List<String> = emptyList()
)

val strawberryCake = Dessert(
    title = "Ciasto Truskawkowe",
    category = "Owocowe",
    cookingTime = "50 min",
    energy = "620 kcal",
    description = "Ten deser jest bardzo smaczny i nietrudny w przygotowaniu. Możesz również zastąpić truskawki dowolnymi innymi jagodami, które lubisz.",
    dessertIngredients = listOf(
        DessertIngredient(R.drawable.flour, "Mąka", "450 g"),
        DessertIngredient(R.drawable.eggs, "Jajka", "4"),
        DessertIngredient(R.drawable.juice, "Sok z cytryny", "150 g"),
        DessertIngredient(R.drawable.strawberry, "Truskawki", "200 g"),
        DessertIngredient(R.drawable.suggar, "Cukier", "1 szklanka"),
        DessertIngredient(R.drawable.mind, "Mięta", "20 g"),
        DessertIngredient(R.drawable.vanilla, "Cukier Waniliowy", "1/2 łyżeczki"),
    ),
    steps = listOf(
        "Wymieszaj mąkę i cukier.",
        "Dodaj jajka i sok z cytryny, dokładnie wymieszaj.",
        "Dodaj pokrojone truskawki i delikatnie wymieszaj.",
        "Piecz w temperaturze 180°C przez około 50 minut.",
        "Podawaj z miętą na wierzchu."
    )
)
