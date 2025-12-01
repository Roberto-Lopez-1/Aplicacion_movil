package com.example.level_up.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.example.level_up.R
import com.example.level_up.model.Articulo
import com.example.level_up.model.Producto
import com.example.level_up.model.Usuario

object AppData {
    val usuario = mutableListOf<Usuario>(
        Usuario("admin", "admin", "admin")
    )

    var usuarioActual: Usuario? = null

    // Mapa para almacenar la imagen de perfil de cada usuario (email -> resourceId)
    val profileImages = mutableStateMapOf<String, Int>()

    val producto = mutableStateListOf(
        Producto(1, "Xbox Series X", 499990.0, R.drawable.xbox),
        Producto(2, "Playstation 5", 790000.0, R.drawable.playstation),
        Producto(3, "PC Gamers", 999990.0, R.drawable.pcgamer)
    )

    val carrito = mutableStateListOf<Articulo>()
}
