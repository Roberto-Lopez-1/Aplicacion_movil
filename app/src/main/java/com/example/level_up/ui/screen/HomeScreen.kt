package com.example.level_up.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.level_up.ui.components.TarjetaProducto
import com.example.level_up.viewmodel.CarritoViewModel
import com.example.level_up.viewmodel.ProductoViewModel
import com.example.level_up.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val productos by productoViewModel.productos.collectAsState()
    val carrito by carritoViewModel.estadoCarrito.collectAsState()
    val context = LocalContext.current

    // State to track if user is admin
    var isAdmin by remember { mutableStateOf(false) }

    // Verify admin status when screen loads
    LaunchedEffect(Unit) {
        productoViewModel.cargarProductos()
        val userId = SessionManager.getCurrentUserId(context)
        Log.d("HomeScreen", "Usuario ID actual: $userId")

        if (userId > 0) {
            val usuario = usuarioViewModel.obtenerUsuarioPorId(userId)
            Log.d("HomeScreen", "Usuario encontrado: ${usuario?.nombre}")

            if (usuario != null) {
                // Check specifically for email "admin" as per requirement
                if (usuario.email == "admin") {
                    Log.d("HomeScreen", "El usuario ES ADMIN")
                    isAdmin = true
                } else {
                    Log.d("HomeScreen", "El usuario NO ES ADMIN")
                    isAdmin = false
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CATÁLOGO", fontWeight = FontWeight.Bold, color = Color.White) },
                actions = {
                    IconButton(onClick = { navController.navigate("Noticias") }) {
                        Icon(Icons.Default.Info, contentDescription = "Noticias", tint = Color(0xFF7289DA))
                    }
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
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { productoViewModel.restaurarProductos() },
                    containerColor = Color(0xFF7289DA),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Restaurar Productos")
                }
            }
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
                    onAddToCart = { carritoViewModel.agregarAlCarrito(it) },
                    onDelete = if (isAdmin) { { productoViewModel.eliminarProducto(producto) } } else null,
                    onEdit = if (isAdmin) { { nombre, precio ->
                        productoViewModel.actualizarProducto(producto.id, nombre, precio)
                    } } else null
                )
            }
        }
    }
}
