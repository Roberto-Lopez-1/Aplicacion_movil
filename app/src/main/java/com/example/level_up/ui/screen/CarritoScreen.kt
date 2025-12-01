package com.example.level_up.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.level_up.data.AppData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    onBack: () -> Unit,
    onNewsClick: () -> Unit
) { 
    val cartItems = AppData.carrito
    val totalPrice = cartItems.sumOf { it.producto.precio * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MI CARRITO", fontWeight = FontWeight.Bold, color = Color(0xFF0034FF)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color(
                            0xFF0034FF
                        )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNewsClick) {
                        Icon(Icons.Default.Info, contentDescription = "Noticias", tint = Color(0xFF00FF00))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${item.cantidad}x", fontSize = 18.sp, color = Color.White)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = item.producto.nombre, fontSize = 18.sp, color = Color.White, modifier = Modifier.weight(1f))
                        Text(text = "$${item.producto.precio * item.cantidad}", fontSize = 18.sp, color = Color(0xFF00FF00))
                    }
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("$${totalPrice}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00FF00))
            }
        }
    }
}
