// Archivo: ClinicaResenasScreen.kt
package com.example.petbook.screens

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
import androidx.compose.runtime.Composable
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
import com.example.petbook.activities.AlberguePerfilActivity
import com.example.petbook.activities.ResenasActivity

@Composable
fun ResenasScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

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
                    .clickable { context.startActivity(Intent(context, AlberguePerfilActivity::class.java)) }
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
                .clickable { context.startActivity(Intent(context, AgregarResenaActivity::class.java)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val comentarios = listOf(
            Triple("mascota1", "Excelente servicio, mucha puntualidad.", "10"),
            Triple("mascota2", "La veterinaria trata muy bien a los animales, 100% recomendable!", "10"),
            Triple("mascota3", "Me gustó, pero el establecimiento es demasiado pequeño.", "8"),
            Triple("mascota4", "Excelente!!! :D", "9.5"),
            Triple("mascota5", "Muy buenos tratamientos", "9")
        )

        comentarios.forEachIndexed { index, (imagen, texto, calificacion) ->
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
                            painter = painterResource(id = R.drawable::class.java.getField(imagen).getInt(null)),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(texto, fontWeight = FontWeight.Medium, fontSize = 16.sp)
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
