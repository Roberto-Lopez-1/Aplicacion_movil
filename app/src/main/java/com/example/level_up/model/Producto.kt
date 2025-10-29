package com.example.level_up.model

import androidx.annotation.DrawableRes

class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    @DrawableRes val imagen: Int // Campo actualizado de `emoji: String` a `imagen: Int`.
)
