package com.example.przepisx.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.przepisx.data.model.Dessert

@Composable
fun EditRecipeForm(
    dessert: Dessert,
    onSave: (Dessert) -> Unit
) {
    var title by remember { mutableStateOf(dessert.title) }
    var category by remember { mutableStateOf(dessert.category) }
    var cookingTime by remember { mutableStateOf(dessert.cookingTime) }
    var energy by remember { mutableStateOf(dessert.energy) }
    var description by remember { mutableStateOf(dessert.description) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Tytuł") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kategoria") })
        OutlinedTextField(value = cookingTime, onValueChange = { cookingTime = it }, label = { Text("Czas Przygotowania") })
        OutlinedTextField(value = energy, onValueChange = { energy = it }, label = { Text("Kalorie") })
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onSave(
                dessert.copy(
                    title = title,
                    category = category,
                    cookingTime = cookingTime,
                    energy = energy,
                    description = description
                )
            )
        }) {
            Text("Zapisz Zmiany")
        }
    }
}