package com.example.petbook.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.components.FormField2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistroMascotaScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    var nombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var tipoMascota = remember { mutableStateMapOf<String, Boolean>() }
    val opcionesMascota = listOf("Perro", "Gato", "Ave", "Roedor", "Otro")
    var aceptarTerminos by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Regresar",
            modifier = Modifier
                .align(Alignment.Start)
                .size(32.dp)
                .clickable {
                    (context as? ComponentActivity)?.finish()
                }
        )
        Text("Crear Perfil MASCOTA", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        FormField2(label = "Nombre:", value = nombre, onValueChange = { nombre = it })
        Spacer(modifier = Modifier.height(8.dp))
        FormField2(label = "Contraseña:", value = contrasena, keyboardType = KeyboardType.Password, isPassword = true, onValueChange = { contrasena = it })
        Spacer(modifier = Modifier.height(8.dp))
        FormField2(label = "Correo Electrónico:", value = correo, keyboardType = KeyboardType.Email, onValueChange = { correo = it })
        Spacer(modifier = Modifier.height(8.dp))
        FormField2(label = "Añade una breve descripción a tu perfil:", value = descripcion, onValueChange = { descripcion = it })

        Spacer(modifier = Modifier.height(16.dp))
        Text("¿Qué animal es tu mascota?", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            opcionesMascota.chunked(3).forEach { fila ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    fila.forEach { tipo ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color.White, RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Checkbox(
                                    checked = tipoMascota[tipo] == true,
                                    onCheckedChange = {
                                        tipoMascota.clear()
                                        tipoMascota[tipo] = it
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.Black,
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color.Black
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(tipo)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        FormField2(label = "Raza (Opcional):", value = raza, onValueChange = { raza = it })
        Spacer(modifier = Modifier.height(12.dp))

        Text("Género:", fontWeight = FontWeight.Medium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Hembra", "Macho").forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = genero == it, onClick = { genero = it })
                    Text(it)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        FormField2(label = "Peso en Kg (Opcional):", value = peso, keyboardType = KeyboardType.Number, onValueChange = { peso = it })
        Spacer(modifier = Modifier.height(12.dp))
        FormField2(label = "Zona, colonia o municipio (Opcional):", value = colonia, onValueChange = { colonia = it })
        Spacer(modifier = Modifier.height(12.dp))
        FormField2(label = "Edad (Opcional):", value = edad, keyboardType = KeyboardType.Number, onValueChange = { edad = it })

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Al registrar una mascota en esta plataforma, aceptas lo siguiente:\n" +
                    "Confirmas que tienes los derechos para usar la imagen que subas y autorizas su uso dentro de la app con fines informativos y sociales.\n" +
                    "Privacidad de ubicación: La ubicación es opcional y se usará para mejorar funciones como adopciones cercanas o contactos relevantes.\n" +
                    "Nos reservamos el derecho de eliminar perfiles con datos ofensivos, falsos o inadecuados.",
            fontSize = 12.sp
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.White, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Checkbox(
                    checked = aceptarTerminos,
                    onCheckedChange = { aceptarTerminos = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text("He leído y acepto los términos y condiciones de PetBook", fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.btn_crear_perfil),
            contentDescription = "Crear perfil",
            modifier = Modifier
                .size(width = 260.dp, height = 60.dp)
                .clickable {
                    if (!aceptarTerminos) {
                        Toast.makeText(context, "Debes aceptar los términos.", Toast.LENGTH_SHORT).show()
                    } else {
                        auth.createUserWithEmailAndPassword(correo, contrasena)
                            .addOnSuccessListener {
                                val tipoSeleccionado = tipoMascota.entries.firstOrNull { it.value }?.key ?: ""
                                val datos = hashMapOf(
                                    "nombre" to nombre,
                                    "contrasena" to contrasena,
                                    "correo" to correo,
                                    "descripcion" to descripcion,
                                    "tipoMascota" to tipoSeleccionado,
                                    "raza" to raza,
                                    "genero" to genero,
                                    "peso" to peso,
                                    "colonia" to colonia,
                                    "edad" to edad,
                                    "fotoUrl" to fotoUrl
                                )

                                db.collection("mascotas")
                                    .add(datos)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Perfil creado exitosamente", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                        (context as? ComponentActivity)?.finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Error al guardar: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Error al registrar usuario: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
        )
    }
}
