package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.level_up.model.Usuario
import com.example.level_up.ui.components.AppButton
import com.example.level_up.ui.components.AppTextField
import com.example.level_up.ui.components.Logo
import com.example.level_up.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegistroScreen(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val viewModel: LoginViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF2C2F33)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(modifier = Modifier.size(150.dp))

            Spacer(Modifier.height(32.dp))

            AppTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre"
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email"
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar Contraseña",
                isPassword = true
            )

            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mensaje,
                    color = if (mensaje.startsWith("✅")) Color.Green else Color.Red,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = "REGISTRARSE",
                color = Color(0xFF7289DA),
                onClick = {
                    if (validarFormulario(nombre, email, password, confirmPassword)) {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val nuevoUsuario = Usuario(
                                    nombre = nombre,
                                    email = email,
                                    password = password
                                )
                                viewModel.registrarUsuario(nuevoUsuario)

                                withContext(Dispatchers.Main) {
                                    mensaje = "✅ ¡Registro exitoso! Redirigiendo..."
                                    CoroutineScope(Dispatchers.IO).launch {
                                        kotlinx.coroutines.delay(1500)
                                        withContext(Dispatchers.Main) {
                                            navController.navigate("login") {
                                                popUpTo("registro") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    mensaje = "❌ Error al registrar usuario"
                                }
                            }
                        }
                    } else {
                        mensaje = "❌ Por favor completa todos los campos correctamente"
                    }
                }
            )

            TextButton(onClick = { navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF7289DA))
            }
        }
    }
}

private fun validarFormulario(
    nombre: String,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    return nombre.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            password.length >= 4
}