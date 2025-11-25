package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import com.example.level_up.model.ItemCarrito
import com.example.level_up.model.Producto

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarritoViewModel : ViewModel() {
    private val _estadoCarrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val estadoCarrito = _estadoCarrito.asStateFlow()

    fun agregarAlCarrito(producto: Producto) {
        val carritoActual = _estadoCarrito.value.toMutableList()
        val itemExistente = carritoActual.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            // Actualizar cantidad
            val indice = carritoActual.indexOf(itemExistente)
            val itemActualizado = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
            carritoActual[indice] = itemActualizado
        } else {
            // Agregar nuevo item
            carritoActual.add(ItemCarrito(producto, 1))
        }

        _estadoCarrito.value = carritoActual
    }

    fun removerDelCarrito(productoId: Int) {
        val carritoActual = _estadoCarrito.value.toMutableList()
        carritoActual.removeAll { it.producto.id == productoId }
        _estadoCarrito.value = carritoActual
    }

    fun actualizarCantidad(productoId: Int, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            removerDelCarrito(productoId)
            return
        }

        val carritoActual = _estadoCarrito.value.toMutableList()
        val indice = carritoActual.indexOfFirst { it.producto.id == productoId }

        if (indice != -1) {
            val itemActualizado = carritoActual[indice].copy(cantidad = nuevaCantidad)
            carritoActual[indice] = itemActualizado
            _estadoCarrito.value = carritoActual
        }
    }

    fun obtenerTotal(): Int {
        return estadoCarrito.value.sumOf { it.producto.precio * it.cantidad }
    }

    fun limpiarCarrito() {
        _estadoCarrito.value = emptyList()
    }
}
