package com.example.przepisx.data.model

import androidx.annotation.DrawableRes

data class Ingredient(
    @DrawableRes
    val image: Int,
    val name: String,
    val quantity: String
)