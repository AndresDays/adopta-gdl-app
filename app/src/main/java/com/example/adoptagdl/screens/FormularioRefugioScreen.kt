package com.example.adoptagdl.screens

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.adoptagdl.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FormularioRefugioScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var nombreRefugio by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var imagenBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imagenUri = uri }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap -> imagenBitmap = bitmap }

    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            db.collection("refugios").document(userId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        nombreRefugio = doc.getString("nombreRefugio") ?: ""
                        direccion = doc.getString("direccion") ?: ""
                        contacto = doc.getString("contacto") ?: ""
                        correo = doc.getString("correo") ?: ""
                    }
                }
                .addOnFailureListener {
                    Log.e("Firebase", "Error cargando datos: ${it.message}")
                }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.albergue_refugio),
            contentDescription = "Fondo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_volver_verde),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(96.dp)
                        .clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(70.dp))

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Seleccionar imagen") },
                    text = { Text("¿De dónde quieres cargar la imagen?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            galeriaLauncher.launch("image/*")
                        }) {
                            Text("Galería")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            camaraLauncher.launch(null)
                        }) {
                            Text("Cámara")
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 36.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                when {
                    imagenBitmap != null -> {
                        Image(
                            bitmap = imagenBitmap!!.asImageBitmap(),
                            contentDescription = "Foto tomada",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    imagenUri != null -> {
                        Image(
                            painter = rememberAsyncImagePainter(imagenUri),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Image(
                            painter = painterResource(id = R.drawable.refugio1),
                            contentDescription = "Refugio Imagen",
                            modifier = Modifier
                                .size(160.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.btn_agregar),
                    contentDescription = "Agregar Imagen",
                    modifier = Modifier
                        .size(36.dp)
                        .offset(x = (-10).dp, y = 10.dp) // ⛔ probablemente lo tienes así o parecido
                        .clickable { showDialog = true }
                )
            }

            FormField("Nombre del refugio", nombreRefugio) { nombreRefugio = it }
            Spacer(modifier = Modifier.height(18.dp))

            FormField("Dirección", direccion) { direccion = it }
            Spacer(modifier = Modifier.height(18.dp))

            FormField("Contacto", contacto) { contacto = it }
            Spacer(modifier = Modifier.height(18.dp))

            FormField("Correo", correo) { correo = it }

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_guardar_cambios_verde),
                contentDescription = "Guardar Cambios",
                modifier = Modifier
                    .width(200.dp)
                    .height(52.dp)
                    .clickable {
                        val datos = mapOf(
                            "nombreRefugio" to nombreRefugio,
                            "direccion" to direccion,
                            "contacto" to contacto,
                            "correo" to correo
                        )
                        db.collection("refugios").document(userId).set(datos)
                            .addOnSuccessListener {
                                onBack() // ✅ Regresa a la pantalla anterior (perfil)
                            }
                            .addOnFailureListener {
                                Log.e("Firebase", "Error guardando datos: ${it.message}")
                            }
                    }

            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FormField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFDEF8E4),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp)
        )
    }
}
