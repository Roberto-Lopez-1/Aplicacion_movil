package com.example.level_up.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up.model.Producto
import com.example.level_up.viewmodel.CarritoViewModel
import kotlinx.coroutines.withContext

@Composable
fun TarjetaProducto(
    producto: com.example.level_up.model.Producto,
    onAddToCart: (Producto) -> Unit
) {
    val context = LocalContext.current
    val name = producto.imagen?.replace(".png", "") ?: "pokeball"
    val resourceId = context.resources.getIdentifier(name, "drawable", context.packageName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF7289DA), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A0A))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.size(150.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = "Imagen de ${producto.nombre}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    producto.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "$${producto.precio}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7289DA)
                )
            }
            Spacer(Modifier.height(16.dp))

            AppButton(
                text = "AGREGAR",
                onClick = { onAddToCart(producto) },
                color = Color(0xFF7289DA)
            )
        }
    }
}