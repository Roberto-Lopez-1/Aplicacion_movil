package com.example.level_up.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.level_up.data.SessionManager
import com.example.level_up.model.Producto
import com.example.level_up.ui.components.EditProductDialog
import com.example.level_up.ui.components.TarjetaProducto
import com.example.level_up.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(navController: NavController, productoViewModel: ProductoViewModel = viewModel()) {
    val productos by productoViewModel.productos.collectAsState()
    var editingProduct by remember { mutableStateOf<Producto?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productoViewModel.cargarProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ADMINISTRADOR", fontWeight = FontWeight.Bold, color = Color.White) },
                actions = {
                    TextButton(onClick = {
                        SessionManager.logout(context)
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }) {
                        Text("SALIR", color = Color(0xFF7289DA), fontWeight = FontWeight.Bold)
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
                    onAddToCart = { /* No-op for admin */ },
                    onEditClick = { editingProduct = it }
                )
            }
        }

        editingProduct?.let { productToEdit ->
            EditProductDialog(
                producto = productToEdit,
                onDismiss = { editingProduct = null },
                onConfirm = { updatedProducto ->
                    productoViewModel.updateProducto(updatedProducto)
                    editingProduct = null
                }
            )
        }
    }
}