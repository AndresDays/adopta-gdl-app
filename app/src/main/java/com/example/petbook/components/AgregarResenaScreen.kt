// Archivo: AgregarResenaScreen.kt
package com.example.petbook.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.AlberguePerfilActivity
import com.example.petbook.activities.ResenasActivity

@Composable
fun AgregarResenaScreen() {
    val context = LocalContext.current
    var puntuacion by remember { mutableStateOf("") }
    var seleccion by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf(TextFieldValue()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFC6DEF1))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp).clickable { context.startActivity(Intent(context, ResenasActivity::class.java)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(84.dp)
            )

            Text(
                text = "33 3966 4304",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(id = R.drawable.albergue1),
            contentDescription = "Foto de perfil",
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

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_mensaje),
            contentDescription = "Mensaje",
            modifier = Modifier
                .width(240.dp)
                .height(72.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("En la escala del 1 al 10, ¿qué tan satisfactoria ha sido tu experiencia con este establecimiento y sus servicios? (Opcional)", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = puntuacion,
            onValueChange = { puntuacion = it },
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("¿Podrías seleccionar que tanto recomendarías este establecimiento y sus servicios a otros usuarios de PetBook?", fontSize = 14.sp)

        val opciones = listOf("RECOMENDABLE", "PUEDE MEJORAR", "NO RECOMENDABLE")
        opciones.forEach { opcion ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = seleccion == opcion,
                    onCheckedChange = { seleccion = opcion },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        checkmarkColor = Color.White,
                        uncheckedColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(opcion, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("¡Añade un comentario o reseña! (Opcional)", fontSize = 14.sp)

        OutlinedTextField(
            value = comentario,
            onValueChange = { comentario = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_enviar),
            contentDescription = "Enviar",
            modifier = Modifier
                .width(180.dp)
                .height(48.dp)
                .clickable {
                    Toast.makeText(context, "Reseña enviada", Toast.LENGTH_SHORT).show()
                    (context as? Activity)?.finish()
                }
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Las reseñas y calificaciones ayudan a que otros usuarios puedan conocer cómo operan las organizaciones o instituciones de salud.",
            fontSize = 11.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
