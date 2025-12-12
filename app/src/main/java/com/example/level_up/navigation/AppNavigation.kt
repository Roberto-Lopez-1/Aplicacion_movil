package com.example.level_up.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level_up.games.GamesScreen
import com.example.level_up.ui.screen.AdminHomeScreen
import com.example.level_up.ui.screen.CarritoScreen
import com.example.level_up.ui.screen.HomeScreen
import com.example.level_up.ui.screen.LoadingScreen
import com.example.level_up.ui.screen.LoginScreen
import com.example.level_up.ui.screen.PerfilScreen
import com.example.level_up.ui.screen.RegistroScreen
import com.example.level_up.viewmodel.CarritoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(navController = navController, startDestination = "loading") {
        composable ("loading"){ LoadingScreen(navController = navController) }
        composable ("login"){ LoginScreen(navController = navController) }
        composable ("registro"){ RegistroScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController, carritoViewModel = carritoViewModel) }
        composable("admin_home") { AdminHomeScreen(navController = navController) }
        composable("games") { GamesScreen(navController = navController) }
        composable("carrito") { CarritoScreen(navController = navController, carritoViewModel = carritoViewModel) }
        composable("perfil") { PerfilScreen(navController) }
    }
}