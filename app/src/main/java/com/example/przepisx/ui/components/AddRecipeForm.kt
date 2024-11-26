package com.example.przepisx.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeForm(
    onSave: (String, String, String, String, String, List<String>, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var preparationTime by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }

    val categories = listOf("Czekoladowe", "Owocowe", "Ciasta", "Lody", "Szybkie Desery")
    val predefinedIngredients = listOf("Mąka", "Cukier", "Masło", "Jajka", "Czekolada", "Śmietana", "Truskawki")
    var selectedIngredients by remember { mutableStateOf(mutableListOf<String>()) }

    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var isIngredientDropdownExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dodaj Nowy Przepis",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Podstawowe Informacje",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tytuł") },
            placeholder = { Text("Wpisz tytuł przepisu") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = isCategoryDropdownExpanded,
            onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                label = { Text("Kategoria") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isCategoryDropdownExpanded) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isCategoryDropdownExpanded,
                onDismissRequest = { isCategoryDropdownExpanded = false }
            ) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            category = cat
                            isCategoryDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Text(
            text = "Składniki",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        ExposedDropdownMenuBox(
            expanded = isIngredientDropdownExpanded,
            onExpandedChange = { isIngredientDropdownExpanded = !isIngredientDropdownExpanded }
        ) {
            OutlinedTextField(
                value = selectedIngredients.joinToString(", "),
                onValueChange = {},
                label = { Text("Dodane składniki") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isIngredientDropdownExpanded) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isIngredientDropdownExpanded,
                onDismissRequest = { isIngredientDropdownExpanded = false }
            ) {
                predefinedIngredients.forEach { ingredient ->
                    DropdownMenuItem(
                        text = { Text(ingredient) },
                        onClick = {
                            if (!selectedIngredients.contains(ingredient)) {
                                selectedIngredients.add(ingredient)
                            }
                            isIngredientDropdownExpanded = false
                        }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 150.dp)
        ) {
            items(selectedIngredients) { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(ingredient, style = MaterialTheme.typography.bodyMedium)
                    IconButton(onClick = { selectedIngredients.remove(ingredient) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Usuń składnik")
                    }
                }
            }
        }

        Text(
            text = "Szczegóły Przepisu",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = preparationTime,
            onValueChange = { preparationTime = it },
            label = { Text("Czas przygotowania (w minutach)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("np. 45") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = calories,
            onValueChange = { calories = it },
            label = { Text("Kalorie") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("np. 200") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis ogólny") },
            placeholder = { Text("Wprowadź opis przepisu...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            label = { Text("Kroki przygotowania") },
            placeholder = { Text("Wpisz kroki przygotowania...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Button(
            onClick = {
                onSave(
                    title, category, preparationTime, calories, description,
                    selectedIngredients.toList(), steps
                )
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Zapisz przepis")
        }
    }
}
