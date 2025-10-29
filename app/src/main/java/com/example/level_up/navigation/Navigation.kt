package com.example.level_up.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.level_up.data.AppData
import com.example.level_up.ui.screen.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "pantalla") {
        composable("pantalla") {
            PantallaScreen { navController.navigate("login") }
        }
        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("catalogo") },
                onRegister = { navController.navigate("registrar") }
            )
        }
        composable("registrar") {
            RegistrarScreen(
                onSuccess = { navController.navigate("login") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("catalogo") {
            CatalogoScreen(
                onProfile = { navController.navigate("perfil") },
                onCartClick = { navController.navigate("carrito") }
            )
        }
        composable("carrito") { 
            CarritoScreen(onBack = { navController.popBackStack() })
        }
        composable("perfil") {
            PerfilScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    AppData.usuarioActual = null
                    AppData.carrito.clear()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                onGoToImageSelector = { navController.navigate("pantalla_selector_imagen") }
            )
        }
        composable("pantalla_selector_imagen") {
            PantallaSelectorImagen(onBack = { navController.popBackStack() })
        }
    }
}