package com.example.level_up.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.ui.components.TarjetaProducto
import com.example.level_up.viewmodel.CatalogoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    catalogoViewModel: CatalogoViewModel = viewModel(),
    onProfile: () -> Unit,
    onCartClick: () -> Unit
) {
    val productos = catalogoViewModel.productos
    val carrito by catalogoViewModel.estadoCarrito.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CATÁLOGO", fontWeight = FontWeight.Bold, color = Color(0xFF0034FF)) },
                actions = {
                    IconButton(onClick = onCartClick) {
                        BadgedBox(badge = {
                            if (carrito.isNotEmpty()) {
                                val totalItems = carrito.sumOf { it.cantidad }
                                Badge(containerColor = Color(0xFF0034FF)) {
                                    Text("$totalItems")
                                }
                            }
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color(0xFF00FF00))
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = onProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color(
                            0xFF0034FF
                        )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productos) { producto ->
                TarjetaProducto(
                    producto = producto,
                    onAgregarClick = { catalogoViewModel.agregarAlCarrito(it) }
                )
            }
        }
    }
}
