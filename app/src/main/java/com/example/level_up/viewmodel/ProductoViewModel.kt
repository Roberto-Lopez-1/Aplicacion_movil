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

    fun updateProducto(producto: Producto) {
        viewModelScope.launch {
            productoDao.actualizar(producto)
            cargarProductos()
        }
    }
}