package com.example.petbook.components

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.LoginActivity
import com.example.petbook.components.FormField2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistroAlbergueScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var responsable by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var integrantes by remember { mutableStateOf("") }
    var necesita by remember { mutableStateOf("") }
    var aceptaVoluntariado by remember { mutableStateOf<String?>(null) }
    var aceptar by remember { mutableStateOf(false) }

    val serviciosKeys = listOf("Adopciones", "Donaciones", "Vacunas", "Esterilizaciones", "Cuidado Temporal", "Otro (Cual)")
    val servicios = remember { serviciosKeys.associateWith { mutableStateOf(false) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBFD9EE))
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Regresar",
            modifier = Modifier
                .align(Alignment.Start)
                .size(32.dp)
                .clickable {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
        )

        Text("Crear Perfil Administrativo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("ALBERGUE O CARIDAD", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        FormField2("Nombre del establecimiento:", nombre) { nombre = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Contraseña:", contrasena, keyboardType = KeyboardType.Password, isPassword = true) { contrasena = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Correo Electrónico:", correo, keyboardType = KeyboardType.Email) { correo = it }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Si es una asociación registrada, subir acta constitutiva o documento de registro:", fontSize = 12.sp)
        Image(painter = painterResource(id = R.drawable.btn_archivo), contentDescription = "Subir archivo", modifier = Modifier.size(width = 200.dp, height = 42.dp).clickable { })
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Nombre del responsable (Completo):", responsable) { responsable = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Ubicación (1 o varias):", ubicacion) { ubicacion = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Hora de Atención:", horario) { horario = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Teléfono del establecimiento:", telefono) { telefono = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Descripción del Albergue u Organización:", descripcion) { descripcion = it }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Agregar foto de perfil:", fontWeight = FontWeight.Medium)
        Box(modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp)).padding(8.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_camara), contentDescription = "Foto perfil", modifier = Modifier.size(80.dp).clickable { })
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Servicios Ofrecidos:", fontWeight = FontWeight.Medium)
        servicios.keys.chunked(3).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                row.forEach { servicio ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(24.dp).background(Color.White, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                            Checkbox(
                                checked = servicios[servicio]?.value == true,
                                onCheckedChange = { isChecked -> servicios[servicio]?.value = isChecked },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.Black,
                                    uncheckedColor = Color.Black,
                                    checkmarkColor = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(servicio)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Nombre de personas que integran el servicio:", integrantes) { integrantes = it }
        Spacer(modifier = Modifier.height(8.dp))
        Text("¿Aceptan voluntarios o donaciones?", fontWeight = FontWeight.Medium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            listOf("Sí", "No").forEach { opcion ->
                Box(modifier = Modifier.size(24.dp).background(Color.White, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                    Checkbox(
                        checked = aceptaVoluntariado == opcion,
                        onCheckedChange = { aceptaVoluntariado = opcion },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Black,
                            checkmarkColor = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(opcion)
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("¿QUÉ SE NECESITA?", fontWeight = FontWeight.Bold, color = Color(0xFF2C4D7E))
        FormField2("", necesita) { necesita = it }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Todos los datos proporcionados deben ser reales y actualizados...", fontSize = 12.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(24.dp).background(Color.White, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                Checkbox(
                    checked = aceptar,
                    onCheckedChange = { aceptar = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White
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
            modifier = Modifier.size(width = 260.dp, height = 60.dp).clickable {
                if (!aceptar) {
                    Toast.makeText(context, "Debes aceptar los términos.", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(correo, contrasena)
                        .addOnSuccessListener { authResult ->
                            val userId = authResult.user?.uid ?: return@addOnSuccessListener
                            val serviciosSeleccionados = servicios.filterValues { it.value }.keys.toList()
                            val datos = hashMapOf(
                                "nombre" to nombre,
                                "correo" to correo,
                                "responsable" to responsable,
                                "ubicacion" to ubicacion,
                                "horario" to horario,
                                "telefono" to telefono,
                                "descripcion" to descripcion,
                                "integrantes" to integrantes,
                                "necesita" to necesita,
                                "aceptaVoluntariado" to aceptaVoluntariado,
                                "servicios" to serviciosSeleccionados
                            )
                            db.collection("albergues").document(userId).set(datos)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Perfil creado exitosamente", Toast.LENGTH_SHORT).show()
                                    context.startActivity(Intent(context, LoginActivity::class.java))
                                    (context as? ComponentActivity)?.finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error al guardar: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error al crear cuenta: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("La confirmación de la creación del perfil puede tardar de 24 a 72 horas debido a la revisión de documentos legales.", fontSize = 10.sp)
    }
}
