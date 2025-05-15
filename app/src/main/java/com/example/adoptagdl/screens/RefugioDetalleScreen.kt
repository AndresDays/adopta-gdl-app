package com.example.adoptagdl.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.activities.ModuloDonacionesActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun RefugioDetalleScreen(refugioId: String) {
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val snapshot = db.collection("refugios").document(refugioId).get().await()
        nombre = snapshot.getString("nombreRefugio") ?: ""
        telefono = snapshot.getString("contacto") ?: ""
        correo = snapshot.getString("correo") ?: ""
        direccion = snapshot.getString("direccion") ?: ""
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_verde),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_volver_verde),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(85.dp)
                        .clickable {
                            (context as? android.app.Activity)?.finish()
                        }
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 8.dp)
                    .size(210.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.refugio1),
                    contentDescription = "Logo Refugio",
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    painter = painterResource(id = R.drawable.btn_agregar_favoritos),
                    contentDescription = "Agregar a favoritos",
                    modifier = Modifier
                        .size(38.dp) // tamaño más pequeño
                        .align(Alignment.BottomEnd)
                        .offset(x = (-28).dp, y = (-38).dp) // sube y mueve a la izquierda
                        .clickable { /* lógica de favoritos */ }
                )
            }



            Text("Refugio ‘$nombre’", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Image(
                    painter = painterResource(id = R.drawable.imagen_refugio),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.imagen_refugio2),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoText(label = "Contacto", values = listOf(telefono, correo))
            Spacer(modifier = Modifier.height(12.dp))
            InfoText(label = "Ubicación", values = listOf(direccion))

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_mascotas_disponibles),
                contentDescription = "Mascotas disponibles",
                modifier = Modifier
                    .height(52.dp)
                    .clickable { }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_quiero_donar),
                contentDescription = "Quiero Donar",
                modifier = Modifier
                    .height(52.dp)
                    .clickable {
                        context.startActivity(Intent(context, ModuloDonacionesActivity::class.java))
                    }
            )
        }

        BottomNavigationBar(current = "search", modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun InfoText(label: String, values: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        values.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDEF8E4), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(text = it)
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
