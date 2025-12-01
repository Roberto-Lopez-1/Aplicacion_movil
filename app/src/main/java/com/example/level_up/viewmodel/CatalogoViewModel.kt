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

    fun eliminarProducto(producto: Producto) {
        AppData.producto.remove(producto)
    }

    fun editarProducto(productoOriginal: Producto, nuevoNombre: String, nuevoPrecio: Double) {
        val index = AppData.producto.indexOf(productoOriginal)
        if (index != -1) {
            val productoActualizado = productoOriginal.copy(nombre = nuevoNombre, precio = nuevoPrecio)
            AppData.producto[index] = productoActualizado
        }
    }
}
