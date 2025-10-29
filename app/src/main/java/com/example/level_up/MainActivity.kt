package com.example.level_up

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.level_up.navigation.AppNavigation
import com.example.level_up.ui.LevelUpTheme // Import corrected

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ahora usamos el tema de la aplicación que hemos definido.
            LevelUpTheme {
                AppNavigation()
            }
        }
    }
}
