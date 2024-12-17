package com.example.przepisx.data.model

import androidx.annotation.DrawableRes

data class DessertIngredient(
    @DrawableRes
    val image: Int = 0,
    val name: String = "",
    val quantity: String = ""
)