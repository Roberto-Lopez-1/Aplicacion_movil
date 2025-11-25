package com.example.level_up.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class SelectorImagenViewModel : ViewModel() {
    var uriImagen by mutableStateOf<String?>(null)
        private set

    fun asignarUriImagen(uri: String?) {
        uriImagen = uri
    }

    fun limpiarImagen() {
        uriImagen = null
    }
}