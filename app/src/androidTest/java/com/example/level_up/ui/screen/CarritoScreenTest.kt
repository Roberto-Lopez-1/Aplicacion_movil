package com.example.level_up.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.level_up.model.ItemCarrito
import com.example.level_up.model.Producto
import com.example.level_up.viewmodel.CarritoViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CarritoScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val carritoViewModel = mockk<CarritoViewModel>(relaxed = true)

    @Test
    fun carritoScreen_muestraTitulo() {
        composeTestRule.setContent {
            CarritoScreen(navController = rememberNavController(), carritoViewModel = CarritoViewModel())
        }

        composeTestRule.onNodeWithText("MI CARRITO").assertIsDisplayed()
    }

    @Test
    fun carritoScreen_muestraItemsDelCarrito() {

        val items = listOf(
            ItemCarrito(
                producto = Producto(
                    id = 1,
                    nombre = "Camiseta Pokémon",
                    precio = 25,
                    imagen = "ic_launcher_foreground"
                ),
                cantidad = 2
            )
        )

        every { carritoViewModel.estadoCarrito } returns MutableStateFlow(items)
        every { carritoViewModel.obtenerTotal() } returns 50

        composeTestRule.setContent {
            CarritoScreen(
                navController = rememberNavController(),
                carritoViewModel = carritoViewModel
            )
        }

        composeTestRule.onNodeWithText("Camiseta Pokémon").assertIsDisplayed()
        composeTestRule.onNodeWithText("$25").assertIsDisplayed()
        composeTestRule.onNodeWithText("Total:").assertIsDisplayed()
        composeTestRule.onNodeWithText("$50").assertIsDisplayed()
    }

}