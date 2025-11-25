package com.example.level_up.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.LevelupDatabase
import com.example.level_up.model.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val database = LevelupDatabase.getDatabase(application)
    private val usuarioDao = database.usuarioDao()

    fun actualizaUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDao.actualizar(usuario)
        }
    }

    suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return usuarioDao.obtenerPorId(id)
    }

    // Función específica para actualizar solo la imagen
    fun actualizarImagenUsuario(id: Int, imagenUri: String?) {
        viewModelScope.launch {
            val usuario = usuarioDao.obtenerPorId(id)
            usuario?.let {
                val usuarioActualizado = it.copy(imagen = imagenUri)
                usuarioDao.actualizar(usuarioActualizado)
            }
        }
    }
}