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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.level_up.ui.components.TarjetaProducto
import com.example.level_up.viewmodel.CarritoViewModel
import com.example.level_up.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, carritoViewModel: CarritoViewModel, productoViewModel: ProductoViewModel = viewModel()) {
    val productos by productoViewModel.productos.collectAsState()
    val carrito by carritoViewModel.estadoCarrito.collectAsState()

    LaunchedEffect(Unit) {
        productoViewModel.cargarProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CATÁLOGO", fontWeight = FontWeight.Bold, color = Color.White) },
                actions = {
                    IconButton(onClick = {navController.navigate("carrito")}) {
                        BadgedBox(badge = {
                            if (carrito.isNotEmpty()) {
                                val totalItems = carrito.sumOf { it.cantidad }
                                Badge(containerColor = Color(0xFF7289DA)) {
                                    Text("$totalItems", color = Color.White)
                                }
                            }
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color(0xFF7289DA))
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = {navController.navigate("perfil")}) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color(0xFF7289DA))
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF23272A))
            )
        },
        containerColor = Color(0xFF2C2F33)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productos) { producto ->
                TarjetaProducto(
                    producto = producto,
                    onAddToCart = { carritoViewModel.agregarAlCarrito(it) })
            }
        }
    }
}