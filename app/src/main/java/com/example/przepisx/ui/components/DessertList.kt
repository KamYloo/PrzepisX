package com.example.przepisx.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.przepisx.ui.screens.Dessert

@Composable
fun DessertList(desserts: List<Dessert>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(desserts) { dessert ->
            DessertCard(dessert)
        }
    }
}