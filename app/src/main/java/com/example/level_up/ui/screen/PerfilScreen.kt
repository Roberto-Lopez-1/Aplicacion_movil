package com.example.level_up.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.level_up.R
import com.example.level_up.data.SessionManager
import com.example.level_up.model.Usuario
import com.example.level_up.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController, usuarioViewModel: UsuarioViewModel = viewModel()) {
    var usuario by remember { mutableStateOf<Usuario?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    // Lanzador para la galería
    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.toString()?.let { uriString ->
            // Actualizar la imagen del usuario
            usuario?.let { usuarioActual ->
                val usuarioId = SessionManager.getCurrentUserId(context)
                usuarioViewModel.actualizarImagenUsuario(usuarioId, uriString)

                // Actualizar el estado local inmediatamente
                usuario = usuarioActual.copy(imagen = uriString)
            }
        }
    }

    // Cargar el usuario cuando se inicialice el composable
    LaunchedEffect(Unit) {
        try {
            val usuarioId = SessionManager.getCurrentUserId(context)
            if (usuarioId > 0) {
                usuario = usuarioViewModel.obtenerUsuarioPorId(usuarioId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MI PERFIL", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color(0xFF7289DA))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        SessionManager.logout(context)
                        navController.navigate("login") {
                            popUpTo("perfil") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar Sesión", tint = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF23272A))
            )
        },
        containerColor = Color(0xFF2C2F33)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFF7289DA))
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFF7289DA), RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A0A))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar del usuario - Click directo a galería
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clickable {
                                    // Abrir galería directamente
                                    lanzadorGaleria.launch("image/*")
                                }
                                .background(Color(0xFF424549), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!usuario?.imagen.isNullOrEmpty()) {
                                // Overlay para indicar que es editable
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = usuario?.imagen),
                                        contentDescription = "Imagen de perfil",
                                        modifier = Modifier
                                            .size(120.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Cambiar imagen",
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                            } else {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(120.dp)
                                            .clip(CircleShape)
                                            .background(Color.Black.copy(alpha = 0.3f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.default_avatar),
                                            contentDescription = "Imagen de perfil",
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                        Icon(
                                            Icons.Default.AddCircle,
                                            contentDescription = "Agregar imagen",
                                            tint = Color.White,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Información del usuario
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = usuario?.nombre ?: "Usuario",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = usuario?.email ?: "",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                        // Botón para eliminar imagen (solo si tiene imagen)
                        if (!usuario?.imagen.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(
                                onClick = {
                                    // Eliminar imagen
                                    usuario?.let { usuarioActual ->
                                        val usuarioId = SessionManager.getCurrentUserId(context)
                                        usuarioViewModel.actualizarImagenUsuario(usuarioId, null)
                                        usuario = usuarioActual.copy(imagen = null)
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Eliminar imagen", color = Color.Red)
                            }
                        }

                        // Botón para editar perfil (otros datos)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7289DA))
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Editar Perfil", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}