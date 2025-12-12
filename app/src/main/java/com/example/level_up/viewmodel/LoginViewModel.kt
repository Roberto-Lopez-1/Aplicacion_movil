package com.example.level_up.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.level_up.data.LevelupDatabase
import com.example.level_up.model.Usuario

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val database = LevelupDatabase.getDatabase(application)
    private val usuarioDao = database.usuarioDao()

    suspend fun login(email: String, password: String): Usuario? {
        return usuarioDao.login(email, password)
    }

    suspend fun registrarUsuario(usuario: Usuario) {
        usuarioDao.insertar(usuario)
    }
}
