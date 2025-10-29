package com.example.level_up.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.data.AppData
import com.example.level_up.model.Usuario
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegistrarViewModel : ViewModel() {

    var nombre by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmarPassword by mutableStateOf("")
        private set

    var error by mutableStateOf<String?>(null)
        private set

    private val _registrationSuccess = MutableSharedFlow<Unit>()
    val registrationSuccess = _registrationSuccess.asSharedFlow()

    fun onNombreChange(newName: String) {
        nombre = newName
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onConfirmarPasswordChange(newConfirm: String) {
        confirmarPassword = newConfirm
    }

    fun onRegisterClick() {
        if (nombre.isBlank() || email.isBlank()) {
            error = "Nombre y email no pueden estar vacíos"
            return
        }
        if (password != confirmarPassword) {
            error = "Las contraseñas no coinciden"
            return
        }

        viewModelScope.launch {
            val newUser = Usuario(email, nombre, password)
            AppData.usuario.add(newUser)
            _registrationSuccess.emit(Unit)
        }
    }
}
