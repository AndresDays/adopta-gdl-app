package com.example.petbook.activities

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.components.FormField2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistroClinicaScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var nombreEst by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var tipoEst = remember { mutableStateMapOf<String, Boolean>().apply {
        put("Clínica/Hospital", false)
        put("Consultorio Particular", false)
    } }
    var responsable by remember { mutableStateOf("") }
    var cedula by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var servicios = remember { mutableStateMapOf<String, Boolean>() }
    var integrantes by remember { mutableStateOf("") }
    var aceptar by remember { mutableStateOf(false) }

    val opcionesTipo = listOf("Clínica/Hospital", "Consultorio Particular")
    val opcionesServicios = listOf("Consultas", "Urgencias", "Cirugías", "Vacunas", "Hospitalización", "Otro (Cual)")

    Column(
        modifier = Modifier
            .fillMaxSize()
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
        Text("SALUD", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        FormField2("Nombre del establecimiento:", nombreEst) { nombreEst = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Contraseña:", contrasena, KeyboardType.Password, true) { contrasena = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Correo Electrónico:", correo, KeyboardType.Email) { correo = it }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tipo de Establecimiento:", fontWeight = FontWeight.Medium)
        opcionesTipo.forEach { tipo ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = tipoEst[tipo] ?: false,
                    onCheckedChange = {
                        tipoEst.keys.forEach { tipoEst[it] = false }
                        tipoEst[tipo] = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(tipo)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Nombre del responsable (Completo):", responsable) { responsable = it }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Cédula Profesional:", fontWeight = FontWeight.Medium)
        Image(
            painter = painterResource(id = R.drawable.btn_archivo),
            contentDescription = "Subir cédula",
            modifier = Modifier.size(width = 200.dp, height = 42.dp).clickable { }
        )

        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Ubicación (1 o varias):", direccion) { direccion = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Descripción:", descripcion) { descripcion = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Hora de Atención:", horario) { horario = it }
        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Teléfono del establecimiento:", telefono) { telefono = it }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Agregar foto de perfil:", fontWeight = FontWeight.Medium)
        Box(
            modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp)).padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_camara),
                contentDescription = "Foto perfil",
                modifier = Modifier.size(80.dp).clickable { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Servicios Ofrecidos:", fontWeight = FontWeight.Medium)
        opcionesServicios.chunked(3).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                row.forEach { servicio ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = servicios[servicio] == true,
                            onCheckedChange = { servicios[servicio] = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(servicio)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        FormField2("Nombre de personas que integran el servicio:", integrantes) { integrantes = it }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Licencia de funcionamiento:", fontWeight = FontWeight.Medium)
        Image(painter = painterResource(id = R.drawable.btn_archivo), contentDescription = "Subir licencia", modifier = Modifier.size(width = 200.dp, height = 42.dp).clickable { })

        Spacer(modifier = Modifier.height(8.dp))
        Text("Comprobante de domicilio:", fontWeight = FontWeight.Medium)
        Image(painter = painterResource(id = R.drawable.btn_archivo), contentDescription = "Subir comprobante", modifier = Modifier.size(width = 200.dp, height = 42.dp).clickable { })

        Spacer(modifier = Modifier.height(12.dp))
        Text("Todos los datos proporcionados deben ser reales y actualizados...", fontSize = 12.sp)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = aceptar,
                onCheckedChange = { aceptar = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.Black
                )
            )
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
                    val tipoSeleccionado = tipoEst.entries.firstOrNull { it.value }?.key ?: ""
                    val serviciosSeleccionados = servicios.filterValues { it }.keys.toList()
                    auth.createUserWithEmailAndPassword(correo, contrasena)
                        .addOnSuccessListener { result ->
                            val user = result.user
                            if (user != null) {
                                val datos = hashMapOf(
                                    "nombreEstablecimiento" to nombreEst,
                                    "correo" to correo,
                                    "tipoEstablecimiento" to tipoSeleccionado,
                                    "responsable" to responsable,
                                    "direccion" to direccion,
                                    "descripcion" to descripcion,
                                    "horario" to horario,
                                    "telefono" to telefono,
                                    "servicios" to serviciosSeleccionados,
                                    "integrantes" to integrantes
                                )
                                db.collection("clinicas").document(user.uid).set(datos)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Perfil creado exitosamente", Toast.LENGTH_SHORT).show()
                                        context.startActivity(Intent(context, LoginActivity::class.java))
                                        (context as? ComponentActivity)?.finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Error al guardar en Firestore: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error al crear usuario: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("La confirmación de la creación del perfil puede tardar de 24 a 72 horas debido a la revisión de documentos legales.", fontSize = 10.sp)
    }
}
