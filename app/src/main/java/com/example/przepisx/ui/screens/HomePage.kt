package com.example.przepisx.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.przepisx.ui.navigation.BottomNavigationBar

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            DessertsPage(modifier,navController)
        }
    }
}
