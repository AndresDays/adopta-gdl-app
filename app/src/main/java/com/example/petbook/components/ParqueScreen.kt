// Archivo: ParqueScreen.kt
package com.example.petbook.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import com.example.petbook.R
import com.example.petbook.activities.ParquesActivity
import com.example.petbook.screens.Parque
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URL

@Composable
fun ParqueScreen() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    var parque by remember { mutableStateOf<Parque?>(null) }
    var comentario by remember { mutableStateOf("") }
    val scroll = rememberScrollState()
    var porcentaje by remember { mutableStateOf(100) }

    LaunchedEffect(Unit) {
        val id = ParquesActivity.parqueSeleccionadoId
        if (id.isNotEmpty()) {
            db.collection("parques").document(id).get()
                .addOnSuccessListener { doc ->
                    val loaded = doc.toObject(Parque::class.java)
                    parque = loaded
                    porcentaje = (loaded?.gusta ?: 0).coerceAtLeast(0)
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC6DEF1))
            .verticalScroll(scroll)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        context.startActivity(Intent(context, ParquesActivity::class.java))
                    }
            )

            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_petbook),
                    contentDescription = "Logo",
                    modifier = Modifier.size(84.dp)
                )
            }

            Spacer(modifier = Modifier.width(32.dp))
        }


        Spacer(modifier = Modifier.height(16.dp))

        parque?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(it.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(5.dp))
                Text(it.direccion, fontSize = 15.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(20.dp))

                val bitmap = loadBitmapFromUrl(it.imagenes.firstOrNull())
                bitmap?.let { img ->
                    Image(
                        bitmap = img,
                        contentDescription = "Parque",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    it.imagenes.drop(1).take(3).forEach { url ->
                        val thumb = loadBitmapFromUrl(url)
                        thumb?.let { bmp ->
                            Image(
                                bitmap = bmp,
                                contentDescription = "Miniatura",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(64.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text("Horario: ${it.horario}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Me Gusta",
                        color = Color(0xFF495C8E),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            val nuevo = (it.gusta + 1).coerceAtMost(100)
                            db.collection("parques").document(ParquesActivity.parqueSeleccionadoId)
                                .update("gusta", nuevo)
                            parque = it.copy(gusta = nuevo)
                            porcentaje = nuevo
                        }
                    )
                    Text(
                        text = "No Me Gusta",
                        color = Color(0xFF495C8E),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            val nuevo = (it.gusta - 1).coerceAtLeast(0)
                            db.collection("parques").document(ParquesActivity.parqueSeleccionadoId)
                                .update("gusta", nuevo)
                            parque = it.copy(gusta = nuevo)
                            porcentaje = nuevo
                        }
                    )
                }

                Text("Al $porcentaje% de los usuarios les gusta este parque.", fontSize = 13.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Escribe un comentario:", fontSize = 13.sp)

                BasicTextField(
                    value = comentario,
                    onValueChange = { comentario = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(32.dp))
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.btn_comentar),
                    contentDescription = "Enviar comentario",
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                        .align(Alignment.End)
                        .clickable {
                            comentario = ""
                        }
                )
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFC6DEF1)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Cargando",
                modifier = Modifier.size(140.dp)
            )
        }

    }
}

fun loadBitmapFromUrl(url: String?): androidx.compose.ui.graphics.ImageBitmap? {
    return try {
        if (url.isNullOrEmpty()) return null
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val input = URL(url).openStream()
        val bitmap = BitmapFactory.decodeStream(input)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}
