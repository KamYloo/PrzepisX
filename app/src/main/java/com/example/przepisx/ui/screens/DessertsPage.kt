package com.example.przepisx.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.przepisx.ui.components.DessertList
import com.example.przepisx.viewModel.DessertViewModel
import com.example.przepisx.viewModel.DessertsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.przepisx.R
import com.example.przepisx.ui.components.CategoryRow
import com.example.przepisx.ui.components.SearchBar

@Composable
fun DessertsPage(modifier: Modifier = Modifier, navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val dessertCategories = listOf("Czekoladowe", "Owocowe", "Ciasta", "Lody", "Szybkie desery")
    val dessertViewModel: DessertViewModel = viewModel()
    val dessertsState by dessertViewModel.dessertsState.collectAsState()

    LaunchedEffect(Unit) {
        dessertViewModel.loadDesserts()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            // Banner Image
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            SearchBar(
                searchQuery = searchQuery.value,
                onQueryChange = { searchQuery.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kategorie",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CategoryRow(
                categories = dessertCategories,
                selectedCategory = selectedCategory.value,
                onCategorySelected = { category ->
                    selectedCategory.value = if (selectedCategory.value == category) null else category
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Przepisy",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            when (dessertsState) {
                is DessertsState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                is DessertsState.Success -> {
                    val desserts = (dessertsState as DessertsState.Success).desserts
                    val filteredDesserts = desserts.filter {
                        (selectedCategory.value == null || it.category == selectedCategory.value) &&
                                it.title.contains(searchQuery.value, ignoreCase = true)
                    }
                    if (filteredDesserts.isNotEmpty()) {
                        DessertList(desserts = filteredDesserts, navController = navController)
                    } else {
                        Text("Brak przepisów", modifier = Modifier.padding(16.dp))
                    }
                }
                is DessertsState.Error -> {
                    Text(
                        text = "Błąd: ${(dessertsState as DessertsState.Error).message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> Unit
            }
        }
    }
}