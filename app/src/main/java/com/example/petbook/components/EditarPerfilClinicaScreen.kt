
package com.example.petbook.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import com.example.petbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun EditarPerfilClinicaScreen() {
    val scrollState = rememberScrollState()
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }
    var repetir by remember { mutableStateOf("") }
    var servicios by remember { mutableStateOf("") }
    val context = LocalContext.current

    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(userId) {
        if (!userId.isNullOrEmpty()) {
            db.collection("clinicas").document(userId).get().addOnSuccessListener { doc ->
                if (doc.exists()) {
                    nombre = doc.getString("nombreEstablecimiento") ?: ""
                    telefono = doc.getString("telefono") ?: ""
                    ubicacion = doc.getString("ubicacion") ?: ""
                    descripcion = doc.getString("descripcion") ?: ""
                    horario = doc.getString("horario") ?: ""
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFC6DEF1))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.Start)
                .size(32.dp)
                .clickable { (context as? ComponentActivity)?.finish() }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_petbook),
            contentDescription = "Logo",
            modifier = Modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("EDITAR PERFIL", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF495C8E))

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.vet1),
            contentDescription = "Foto",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(140.dp).clip(CircleShape)
        )

        Text("Cambiar foto de perfil", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))

        InputField("Nombre:", nombre) { nombre = it }
        InputField("Teléfono:", telefono) { telefono = it }
        InputField("Ubicación:", ubicacion) { ubicacion = it }
        InputField("Descripción:", descripcion) { descripcion = it }
        InputField("Horario:", horario) { horario = it }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Cambio de contraseña", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(6.dp))

        InputField("Contraseña actual:", actual) { actual = it }
        InputField("Contraseña nueva:", nueva) { nueva = it }
        InputField("Repetir nueva contraseña:", repetir) { repetir = it }

        Spacer(modifier = Modifier.height(8.dp))
        InputField("Quitar o agregar Servicios", servicios) { servicios = it }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_guardar),
            contentDescription = "Guardar",
            modifier = Modifier
                .width(220.dp)
                .height(52.dp)
                .clickable {
                    if (!userId.isNullOrEmpty()) {
                        val clinicaRef = db.collection("clinicas").document(userId)
                        clinicaRef.update(
                            mapOf(
                                "nombreEstablecimiento" to nombre,
                                "telefono" to telefono,
                                "ubicacion" to ubicacion,
                                "descripcion" to descripcion,
                                "horario" to horario
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(context, ClinicaPerfilActivity::class.java))
                        }.addOnFailureListener {
                            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        )
    }
}
