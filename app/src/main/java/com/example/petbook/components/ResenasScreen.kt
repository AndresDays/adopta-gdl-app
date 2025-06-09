package com.example.petbook.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.AgregarResenaActivity
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ResenasScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val clinicaId = (context as? Activity)?.intent?.getStringExtra("clinicaId") ?: ""
    val db = FirebaseFirestore.getInstance()
    var comentarios by remember { mutableStateOf(listOf<Triple<String, String, String>>()) }

    // Cargar reseñas desde Firebase solo una vez
    LaunchedEffect(clinicaId) {
        if (clinicaId.isNotBlank()) {
            db.collection("clinicas")
                .document(clinicaId)
                .collection("resenas")
                .get()
                .addOnSuccessListener { result ->
                    val lista = result.documents.map { doc ->
                        Triple(
                            doc.getString("nombreMascota") ?: "Anónimo",
                            doc.getString("comentario") ?: "Sin comentario",
                            doc.getString("calificacion") ?: "N/A"
                        )
                    }
                    comentarios = lista
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC6DEF1))
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(32.dp)
                    .clickable { (context as? Activity)?.finish() }
            )

            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(84.dp)
            )

            Text(
                "33 3966 4304",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.albergue1),
            contentDescription = "Albergue",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("MayPet", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF495C8E))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Place, contentDescription = null, tint = Color.Black)
            Text("GDL", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_calificar),
            contentDescription = "Calificar",
            modifier = Modifier
                .width(120.dp)
                .height(50.dp)
                .clickable { context.startActivity(Intent(context, AgregarResenaActivity::class.java).apply {
                    putExtra("clinicaId", clinicaId)
                }) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        comentarios.forEach { (nombre, texto, calificacion) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.mascota1),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(texto, fontSize = 14.sp)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 20.dp, y = (-20).dp)
                        .background(Color(0xFF3DD9C1), shape = RoundedCornerShape(24.dp))
                        .padding(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    Text(calificacion, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
