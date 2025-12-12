package com.example.level_up.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.level_up.model.Producto
import com.example.level_up.viewmodel.CarritoViewModel
import com.example.level_up.viewmodel.ProductoViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun homeScreen_muestraTitulo() {
        composeTestRule.setContent {
            HomeScreen(navController = rememberNavController(), carritoViewModel = CarritoViewModel())
        }

        composeTestRule.onNodeWithText("CATÁLOGO").assertIsDisplayed()
    }

    @Test
    fun homeScreen_muestraProductosMockeados() {

        // Mock de viewmodels
        val productoViewModel = mockk<ProductoViewModel>(relaxed = true)
        val carritoViewModel = mockk<CarritoViewModel>(relaxed = true)

        // Lista mockeada
        val productosFake = listOf(
            Producto(1, "Producto A", 10, "ic_launcher_foreground"),
            Producto(2, "Producto B", 20, "ic_launcher_foreground")
        )

        every { productoViewModel.productos } returns MutableStateFlow(productosFake)
        every { carritoViewModel.estadoCarrito } returns MutableStateFlow(emptyList())
        coEvery { productoViewModel.cargarProductos() } just Runs

        composeTestRule.setContent {
            HomeScreen(
                navController = rememberNavController(),
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel
            )
        }

        // Verificar en pantalla productos falsos
        composeTestRule.onNodeWithText("Producto A").assertIsDisplayed()
        composeTestRule.onNodeWithText("Producto B").assertIsDisplayed()
    }
}