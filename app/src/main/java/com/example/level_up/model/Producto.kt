package com.example.level_up.model

import androidx.annotation.DrawableRes

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    @DrawableRes val imagen: Int
)
