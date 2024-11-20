package com.example.przepisx.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.przepisx.R
import com.example.przepisx.ui.components.CategoryRow
import com.example.przepisx.ui.components.DessertList
import com.example.przepisx.ui.components.SearchBar

@Composable
fun DessertsPage(modifier: Modifier = Modifier, navController: NavController) {
    val searchQuery = remember { mutableStateOf("") }
    val dessertCategories = listOf("Czekoladowe", "Owocowe", "Ciasta", "Lody", "Szybkie desery")
    val dessertList = listOf(
        Dessert("Sernik na zimno", "Pyszny deser owocowy", R.drawable.sernik),
        Dessert("Brownie", "Czekoladowe i wilgotne", R.drawable.brownie),
        Dessert("Tiramisu", "Klasyczny włoski deser", R.drawable.tiramisu),
        Dessert("Panna Cotta", "Lekka i kremowa", R.drawable.pannacotta),
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
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
            CategoryRow(categories = dessertCategories)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Przepisy",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            DessertList(desserts = dessertList)
        }
    }
}

data class Dessert(val name: String, val description: String, val imageRes: Int)
