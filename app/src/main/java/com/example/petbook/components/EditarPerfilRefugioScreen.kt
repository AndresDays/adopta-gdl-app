package com.example.petbook.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.AlberguePerfilActivity
import com.example.petbook.activities.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditarPerfilRefugioScreen(onBack: () -> Unit) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var contrasenaActual by remember { mutableStateOf("") }
    var contrasenaNueva by remember { mutableStateOf("") }
    var contrasenaRepetir by remember { mutableStateOf("") }
    var servicios by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            db.collection("albergues").document(userId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        nombre = doc.getString("nombre") ?: ""
                        telefono = doc.getString("telefono") ?: ""
                        ubicacion = doc.getString("ubicacion") ?: ""
                        descripcion = doc.getString("descripcion") ?: ""
                        horario = doc.getString("horario") ?: ""
                        val serviciosList = doc.get("servicios") as? List<*>
                        servicios = serviciosList?.joinToString(", ") ?: ""
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC6DEF1))
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Volver",
            modifier = Modifier
                .align(Alignment.Start)
                .size(32.dp)
                .clickable { onBack() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_petbook),
            contentDescription = null,
            modifier = Modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("EDITAR PERFIL", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF495C8E))

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.albergue1),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text("Cambiar foto de perfil", fontSize = 13.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))
        FormField("Nombre:", nombre) { nombre = it }
        FormField("Teléfono:", telefono) { telefono = it }
        FormField("Ubicación:", ubicacion) { ubicacion = it }
        FormField("Descripción:", descripcion) { descripcion = it }
        FormField("Horario:", horario) { horario = it }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Cambio de contraseña", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 13.sp)

        FormField("Contraseña actual:", contrasenaActual) { contrasenaActual = it }
        FormField("Contraseña nueva:", contrasenaNueva) { contrasenaNueva = it }
        FormField("Repetir nueva contraseña:", contrasenaRepetir) { contrasenaRepetir = it }

        FormField("Quitar o agregar Servicios", servicios) { servicios = it }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.tarjeta),
                contentDescription = "Tarjeta",
                modifier = Modifier
                    .width(220.dp)
                    .height(110.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text("Modificar", fontWeight = FontWeight.Bold)
                Text("Eliminar", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_guardar),
            contentDescription = "Guardar",
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .clickable {
                    val datos = mapOf(
                        "nombre" to nombre,
                        "telefono" to telefono,
                        "ubicacion" to ubicacion,
                        "descripcion" to descripcion,
                        "horario" to horario,
                        "servicios" to servicios.split(",").map { it.trim() }
                    )
                    db.collection("albergues").document(userId).set(datos).addOnSuccessListener {
                        Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, AlberguePerfilActivity::class.java))
                    }
                }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FormField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.width(130.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 15.sp),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        )
    }
}
