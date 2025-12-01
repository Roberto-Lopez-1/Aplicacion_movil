package com.example.level_up.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.LevelupDatabase
import com.example.level_up.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application){
    private val database = LevelupDatabase.getDatabase(application)
    private val productoDao = database.productoDao()

    private val _productos = MutableStateFlow(emptyList<Producto>())
    val productos: StateFlow<List<Producto>> = _productos

    fun cargarProductos() {
        viewModelScope.launch {
            val lista = productoDao.obtenerTodos()
            _productos.value = lista
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.eliminar(producto)
            // Refresh the list immediately after deletion to update UI
            cargarProductos()
        }
    }

    fun actualizarProducto(id: Int, nombre: String, precio: Double) {
        viewModelScope.launch {
            val producto = productoDao.obtenerPorId(id)
            if (producto != null) {
                // FIX: Ensure we convert the Double price to Int safely if your DB uses Int
                val productoActualizado = producto.copy(
                    nombre = nombre,
                    precio = precio.toInt()
                )
                productoDao.actualizar(productoActualizado)
                // Refresh the list immediately after update to update UI
                cargarProductos()
            }
        }
    }
}
