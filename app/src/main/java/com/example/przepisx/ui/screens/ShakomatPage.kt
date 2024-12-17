package com.example.przepisx.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

import com.example.przepisx.utils.ShakeDetector
import com.example.przepisx.viewModel.ShakomatViewModel

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.HtmlCompat

@Composable
fun ShakomatPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel = remember { ShakomatViewModel() }
    val recipe by viewModel.recipe.collectAsState()

    val shakeDetector = remember {
        ShakeDetector(context) {
            viewModel.fetchRandomRecipe()
        }
    }

    DisposableEffect(Unit) {
        shakeDetector.start()
        onDispose { shakeDetector.stop() }
    }

    if (recipe == null) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Potrząśnij telefonem, aby wylosować przepis!",
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center
            )
        }
    } else {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = recipe!!.title.orEmpty(),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberImagePainter(recipe!!.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                SectionHeader(text = "Ingredients:", backgroundColor = Color.LightGray, contentColor = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(recipe!!.extendedIngredients.orEmpty()) { ingredient ->
                Text(
                    text = "${ingredient.amount} ${ingredient.unit} ${ingredient.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                SectionHeader(text = "Instructions:", backgroundColor = Color.LightGray, contentColor = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
            }

            val steps = parseHtmlToSteps(recipe!!.instructions.orEmpty())

            items(steps) { step ->
                Text(
                    text = step,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
@Composable
fun SectionHeader(text: String, backgroundColor: Color, contentColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = contentColor
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
    }
}

fun parseHtmlToSteps(html: String): List<String> {
    val plainText = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    return plainText.split("\n").map { it.trim() }.filter { it.isNotEmpty() }
}