package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.ui.components.AppButton
import com.example.level_up.ui.components.AppTextField
import com.example.level_up.ui.components.Logo
import com.example.level_up.viewmodel.RegistrarViewModel

@Composable
fun RegistrarScreen(
    registrarViewModel: RegistrarViewModel = viewModel(),
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(registrarViewModel.registrationSuccess) {
        registrarViewModel.registrationSuccess.collect {
            onSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()

            Spacer(Modifier.height(32.dp))

            AppTextField(
                value = registrarViewModel.nombre,
                onValueChange = { registrarViewModel.onNombreChange(it) },
                label = "Nombre"
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = registrarViewModel.email,
                onValueChange = { registrarViewModel.onEmailChange(it) },
                label = "Email"
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = registrarViewModel.password,
                onValueChange = { registrarViewModel.onPasswordChange(it) },
                label = "Contraseña",
                isPassword = true
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = registrarViewModel.confirmarPassword,
                onValueChange = { registrarViewModel.onConfirmarPasswordChange(it) },
                label = "Confirmar Contraseña",
                isPassword = true
            )

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = "REGISTRARSE",
                onClick = { registrarViewModel.onRegisterClick() }
            )

            TextButton(onClick = onBack) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF00FF00))
            }

            registrarViewModel.error?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}
