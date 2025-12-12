package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.level_up.data.SessionManager
import com.example.level_up.ui.components.Logo
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000)

        if (SessionManager.isLoggedIn(context)) {
            navController.navigate("home") {
                popUpTo("loading") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("loading") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Logo()
    }
}