package com.example.adoptagdl.screens

import android.app.Activity
import android.content.Intent
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.activities.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EditarPerfilAdoptanteScreen() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var nombre by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var tipoMascota by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        userId?.let { uid ->
            val doc = db.collection("adoptantes").document(uid).get().await()
            nombre = doc.getString("nombre") ?: ""
            colonia = doc.getString("colonia") ?: ""
            tipoMascota = doc.getString("tipoMascota") ?: ""
            correo = doc.getString("correo") ?: ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_adoptante),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_volver),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(85.dp)
                        .clickable { (context as? Activity)?.finish() }
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier.size(165.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fotoyo3),
                    contentDescription = "Foto Perfil",
                    modifier = Modifier
                        .size(155.dp)
                        .background(Color.LightGray, CircleShape)
                        .clip(CircleShape)
                )
                Image(
                    painter = painterResource(id = R.drawable.btn_agregar),
                    contentDescription = "Agregar imagen",
                    modifier = Modifier
                        .size(36.dp)
                        .offset(x = (-6).dp, y = (-6).dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormField2("Nombre", nombre) { nombre = it }
            Spacer(modifier = Modifier.height(12.dp))

            FormField2("Colonia", colonia) { colonia = it }
            Spacer(modifier = Modifier.height(12.dp))

            FormField2("Tipo de mascota deseada", tipoMascota) { tipoMascota = it }
            Spacer(modifier = Modifier.height(12.dp))

            FormField2("Correo", correo) { correo = it }
            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_guardar_cambios_azul),
                contentDescription = "Guardar Cambios",
                modifier = Modifier
                    .height(50.dp)
                    .clickable {
                        userId?.let { uid ->
                            val data = mapOf(
                                "nombre" to nombre,
                                "colonia" to colonia,
                                "tipoMascota" to tipoMascota,
                                "correo" to correo
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    db.collection("adoptantes").document(uid).update(data).await()
                                    (context as? Activity)?.runOnUiThread {
                                        context.startActivity(Intent(context, ProfileActivity::class.java))
                                        (context as Activity).finish()
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
            )
        }

        BottomNavigationBar(current = "profile", modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun FormField2(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDDEEFF),
                unfocusedContainerColor = Color(0xFFDDEEFF),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}
