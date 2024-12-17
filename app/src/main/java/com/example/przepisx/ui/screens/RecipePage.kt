package com.example.przepisx.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.przepisx.R
import com.example.przepisx.data.model.Dessert
import com.example.przepisx.ui.theme.Pink40
import com.example.przepisx.viewModel.DessertDetailsState
import com.example.przepisx.viewModel.DessertViewModel

@Composable
fun RecipePage(recipeId: String, navController: NavController, viewModel: DessertViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val dessertDetailsState by viewModel.dessertDetailsState.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.loadDessertById(recipeId)
    }

    Box {
        val scrollState = rememberLazyListState()

        when (dessertDetailsState) {
            is DessertDetailsState.Loading -> {
                Text("Loading...", modifier = Modifier.align(Alignment.Center))
            }
            is DessertDetailsState.Success -> {
                val dessert = (dessertDetailsState as DessertDetailsState.Success).dessert
                Content(dessert, scrollState)

                TopAppBar(
                    contentPadding = PaddingValues(), backgroundColor = White, modifier = Modifier.height(250.dp)
                ) {
                    Column {
                        Box(Modifier.height(200.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.strawberry_pie_1),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(
                                        Brush.verticalGradient(
                                            colorStops = arrayOf(
                                                Pair(0.4f, Transparent),
                                                Pair(1f, White)
                                            )
                                        )
                                    )
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    dessert.category,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .background(LightGray)
                                        .padding(vertical = 6.dp, horizontal = 16.dp)
                                )
                            }
                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                dessert.title,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
            is DessertDetailsState.Error -> {
                Text(
                    text = (dessertDetailsState as DessertDetailsState.Error).message,
                    modifier = Modifier.align(Alignment.Center),
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }
            is DessertDetailsState.Idle -> {
            }
        }
    }
}

@Composable
fun Content(dessert: Dessert, scrollState: LazyListState) {
    var selectedTab by remember { mutableStateOf("Składniki") }

    LazyColumn(contentPadding = PaddingValues(top = 250.dp), state = scrollState) {
        item {
            BasicInfo(dessert)
            Description(dessert)

            IngredientsHeader(selectedTab) { tab ->
                selectedTab = tab
            }

            if (selectedTab == "Składniki") {
                IngredientsList(dessert)
            } else {
                StepsList(dessert)
            }
        }
    }
}

@Composable
fun IngredientsList(dessert: Dessert) {
    EasyGrid(nColumns = 3, items = dessert.dessertIngredients) {
        IngredientCard(it.image, it.name, it.quantity, Modifier)
    }
}


@Composable
fun <T> EasyGrid(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        for (i in items.indices step nColumns) {
            Row {
                for (j in 0 until nColumns) {
                    if (i + j < items.size) {
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(Modifier.weight(1f, fill = true))
                    }
                }
            }
        }
    }
}


@Composable
fun IngredientCard(
    @DrawableRes iconResource: Int,
    title: String,
    subtitle: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Card(
            elevation = 0.dp,
            backgroundColor = LightGray,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = iconResource),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(text = title, modifier = Modifier.width(100.dp), fontSize = 14.sp, fontWeight = Medium)
        Text(text = subtitle, color = DarkGray, modifier = Modifier.width(100.dp), fontSize = 14.sp)
    }
}


@Composable
fun IngredientsHeader(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton("Składniki", selectedTab == "Składniki", Modifier.weight(1f)) {
            onTabSelected("Składniki")
        }
        TabButton("Kroki Przygotowania", selectedTab == "Kroki Przygotowania", Modifier.weight(1f)) {
            onTabSelected("Kroki Przygotowania")
        }
    }
}

@Composable
fun TabButton(text: String, active: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        elevation = null,
        colors = if (active) ButtonDefaults.buttonColors(
            backgroundColor = Pink40,
            contentColor = White
        ) else ButtonDefaults.buttonColors(
            backgroundColor = LightGray,
            contentColor = DarkGray
        )
    ) {
        Text(text)
    }
}


@Composable
fun Description(dessert: Dessert) {
    Text(
        text = dessert.description,
        fontWeight = Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun BasicInfo(dessert: Dessert) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_clock, dessert.cookingTime+" min")
        InfoColumn(R.drawable.ic_flame, dessert.energy+" kcal")
    }
}

@Composable
fun InfoColumn(@DrawableRes iconResouce: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResouce),
            contentDescription = null,
            tint = Pink40,
            modifier = Modifier.height(24.dp)
        )
        Text(text = text, fontWeight = Bold)
    }
}

@Composable
fun StepsList(dessert: Dessert) {
    Column(modifier = Modifier.padding(16.dp)) {
        dessert.steps.forEachIndexed { index, step ->
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = "${index + 1}. ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = step,
                    fontSize = 16.sp
                )
            }
        }
    }
}


