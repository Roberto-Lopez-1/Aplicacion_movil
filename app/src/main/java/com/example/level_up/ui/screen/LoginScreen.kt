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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.level_up.data.SessionManager
import com.example.level_up.ui.components.AppButton
import com.example.level_up.ui.components.AppTextField
import com.example.level_up.ui.components.Logo
import com.example.level_up.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val context = LocalContext.current

    val viewModel: LoginViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF2C2F33)),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )  {
            Logo(modifier = Modifier.size(150.dp))

            Spacer(Modifier.height(32.dp))

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

            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = if (mensaje == "¡Login correcto!") Color.Green else Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = "INICIAR SESIÓN",
                color = Color(0xFF7289DA),
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val usuario = viewModel.login(email, password)
                        withContext(Dispatchers.Main) {
                            if (usuario != null) {
                                // Guardar la sesión del usuario
                                SessionManager.saveUserSession(context, usuario.id)
                                mensaje = "¡Login correcto!"
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                mensaje = "Email o contraseña incorrectos"
                            }
                        }
                    }
                }
            )

            TextButton(onClick = {navController.navigate("registro")}) {
                Text("¿No tienes cuenta? Regístrate", color = Color(0xFF7289DA))
            }
        }
    }
}