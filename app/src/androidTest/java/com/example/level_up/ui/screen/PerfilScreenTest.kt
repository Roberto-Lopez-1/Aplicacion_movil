package com.example.level_up.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.level_up.data.SessionManager
import com.example.level_up.model.Usuario
import com.example.level_up.viewmodel.UsuarioViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Rule
import org.junit.Test

class PerfilScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun perfilScreen_muestraTitulo() {
        composeTestRule.setContent {
            PerfilScreen(navController = rememberNavController())
        }

        composeTestRule.onNodeWithText("MI PERFIL").assertIsDisplayed()
    }
    @Test
    fun perfilScreen_muestraDatosDelUsuario() {
        // Mockear SessionManager para que devuelva un ID válido
        mockkObject(SessionManager)
        every { SessionManager.getCurrentUserId(any()) } returns 1

        val usuarioViewModel = mockk<UsuarioViewModel>(relaxed = true)

        val usuarioMock = Usuario(
            id = 1,
            nombre = "Manolito",
            email = "manolito@duocuc.cl",
            password = "password",
            imagen = null
        )

        // Mock de la función del ViewModel
        coEvery { usuarioViewModel.obtenerUsuarioPorId(1) } returns usuarioMock

        composeTestRule.setContent {
            PerfilScreen(
                navController = rememberNavController(),
                usuarioViewModel = usuarioViewModel
            )
        }

        // Esperar a que el LaunchedEffect cargue el usuario
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithText("Manolito", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Manolito", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("manolito@duocuc.cl").assertIsDisplayed()
    }

    @Test
    fun perfilScreen_muestraImagenSiExiste() {

        val usuarioViewModel = mockk<UsuarioViewModel>(relaxed = true)

        val usuarioMock = Usuario(
            id = 1,
            nombre = "Ash",
            email = "ash@pokemon.com",
            password = "password",
            imagen = "https://fakeurl.com/avatar.jpg"
        )

        coEvery { usuarioViewModel.obtenerUsuarioPorId(any()) } returns usuarioMock

        composeTestRule.setContent {
            PerfilScreen(
                navController = rememberNavController(),
                usuarioViewModel = usuarioViewModel
            )
        }

        // Imagen en pantalla por descripción
        composeTestRule
            .onNodeWithContentDescription("Imagen de perfil")
            .assertExists()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}