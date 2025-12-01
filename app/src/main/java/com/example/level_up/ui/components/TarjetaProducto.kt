package com.example.level_up.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.level_up.model.Producto

@Composable
fun TarjetaProducto(
    producto: Producto,
    onAddToCart: (Producto) -> Unit,
    onDelete: (() -> Unit)? = null,          // Must be nullable
    onEdit: ((String, Double) -> Unit)? = null // Must be nullable
) {
    // State for the Edit Dialog
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF23272A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "$${producto.precio}", color = Color(0xFF99AAB5))
            }

            Row {
                // REGULAR USER BUTTON
                IconButton(onClick = { onAddToCart(producto) }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color(0xFF7289DA))
                }

                // ADMIN BUTTONS - Only show if function is not null
                if (onEdit != null) {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Yellow)
                    }
                }

                if (onDelete != null) {
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                    }
                }
            }
        }
    }

    // DIALOG FOR EDITING
    if (showEditDialog && onEdit != null) {
        var editName by remember { mutableStateOf(producto.nombre) }
        var editPrice by remember { mutableStateOf(producto.precio.toString()) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar Producto") },
            text = {
                Column {
                    TextField(value = editName, onValueChange = { editName = it }, label = { Text("Nombre") })
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(value = editPrice, onValueChange = { editPrice = it }, label = { Text("Precio") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    val priceDouble = editPrice.toDoubleOrNull() ?: 0.0
                    onEdit(editName, priceDouble)
                    showEditDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

