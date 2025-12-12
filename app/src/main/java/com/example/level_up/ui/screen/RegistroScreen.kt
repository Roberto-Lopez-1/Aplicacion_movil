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

    var nombreError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

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
                onValueChange = {
                    nombre = it
                    nombreError = false },
                label = "Nombre",
                isError = nombreError
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false },
                label = "Email",
                isError = emailError
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false},
                label = "Contraseña",
                isPassword = true,
                isError = passwordError
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = false},
                label = "Confirmar Contraseña",
                isPassword = true,
                isError = confirmPasswordError
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

                    nombreError = nombre.isBlank()
                    emailError = email.isBlank()
                    passwordError = password.isBlank() || password.length < 4
                    confirmPasswordError = confirmPassword.isBlank() || confirmPassword != password

                    if (nombreError||emailError||passwordError||confirmPasswordError) {
                        mensaje = "❌ Por favor completa todos los campos correctamente"
                        return@AppButton
                    }


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
                }
            )

            TextButton(onClick = { navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF7289DA))
            }
        }
    }
}
