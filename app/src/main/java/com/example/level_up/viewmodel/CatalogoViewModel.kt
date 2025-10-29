package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import com.example.level_up.data.AppData
import com.example.level_up.model.Articulo
import com.example.level_up.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatalogoViewModel : ViewModel() {

    val productos = AppData.producto

    private val _estadoCarrito = MutableStateFlow(AppData.carrito.toList())
    val estadoCarrito = _estadoCarrito.asStateFlow()

    fun agregarAlCarrito(producto: Producto) {
        val articuloExistente = AppData.carrito.find { it.producto.id == producto.id }

        if (articuloExistente != null) {
            val indice = AppData.carrito.indexOf(articuloExistente)
            val articuloActualizado = articuloExistente.copy(cantidad = articuloExistente.cantidad + 1)
            AppData.carrito[indice] = articuloActualizado
        } else {
            AppData.carrito.add(Articulo(producto, 1))
        }

        _estadoCarrito.value = AppData.carrito.toList()
    }
}
