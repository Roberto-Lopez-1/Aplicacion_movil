package com.example.level_up.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.level_up.model.Producto

@Composable
fun EditProductDialog(
    producto: Producto,
    onDismiss: () -> Unit,
    onConfirm: (Producto) -> Unit
) {
    var nombre by remember { mutableStateOf(producto.nombre) }
    var precio by remember { mutableStateOf(producto.precio.toString()) }
    var imagen by remember { mutableStateOf(producto.imagen ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Editar Producto") },
        text = {
            Column {
                AppTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(value = precio, onValueChange = { precio = it }, label = "Precio")
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(value = imagen, onValueChange = { imagen = it }, label = "URL de la Imagen")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val updatedProducto = producto.copy(
                        nombre = nombre,
                        precio = precio.toIntOrNull() ?: producto.precio,
                        imagen = imagen
                    )
                    onConfirm(updatedProducto)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}