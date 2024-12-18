package com.example.przepisx.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.przepisx.R
import com.example.przepisx.data.model.DessertIngredient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeForm(
    onSave: (String, String, String, String, String, List<DessertIngredient>, List<String>) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var preparationTime by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }

    val categories = listOf("Czekoladowe", "Owocowe", "Ciasta", "Lody", "Szybkie Desery")
    val predefinedDessertIngredients = listOf(
        DessertIngredient(R.drawable.flour, "Mąka", ""),
        DessertIngredient(R.drawable.suggar, "Cukier", ""),
        DessertIngredient(R.drawable.eggs, "Jajka", ""),
        DessertIngredient(R.drawable.strawberry, "Truskawki", ""),
        DessertIngredient(R.drawable.kakao, "Kakao", ""),
        DessertIngredient(R.drawable.milk_powder, "Mleko w Proszku", ""),
        DessertIngredient(R.drawable.cream_30, "Smietana30%", ""),
        DessertIngredient(R.drawable.cream_36, "Smietana36%", ""),
        DessertIngredient(R.drawable.mascarpone, "Mascarpone", ""),
        DessertIngredient(R.drawable.chocolate, "Chocolate", ""),
        DessertIngredient(R.drawable.oil, "Oil", "")
    )

    val selectedDessertIngredients = remember { mutableStateListOf<DessertIngredient>() }
    var selectedIngredient by remember { mutableStateOf(predefinedDessertIngredients.first()) }
    var ingredientAmount by remember { mutableStateOf("") }

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
            text = "Dodaj Nowy Przepis z Listą Składników",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
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

        // Sekcja składników
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
                value = selectedIngredient.name,
                onValueChange = {},
                label = { Text("Składnik") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isIngredientDropdownExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isIngredientDropdownExpanded,
                onDismissRequest = { isIngredientDropdownExpanded = false }
            ) {
                predefinedDessertIngredients.forEach { ingredient ->
                    DropdownMenuItem(
                        text = { Text(ingredient.name) },
                        onClick = {
                            selectedIngredient = ingredient
                            isIngredientDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = ingredientAmount,
            onValueChange = { ingredientAmount = it },
            label = { Text("Ilość") },
            placeholder = { Text("np. 200g") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (ingredientAmount.isNotBlank()) {
                    selectedDessertIngredients.add(
                        selectedIngredient.copy(quantity = ingredientAmount)
                    )
                    ingredientAmount = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Dodaj Składnik")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
        ) {
            items(selectedDessertIngredients) { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = ingredient.image),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp).padding(end = 8.dp)
                        )
                        Text("${ingredient.name} - ${ingredient.quantity}", style = MaterialTheme.typography.bodyMedium)
                    }
                    IconButton(onClick = { selectedDessertIngredients.remove(ingredient) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Usuń składnik")
                    }
                }
            }
        }

        // Reszta formularza
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
                    selectedDessertIngredients, steps.split("\n")
                )
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Zapisz Przepis")
        }
    }
}
