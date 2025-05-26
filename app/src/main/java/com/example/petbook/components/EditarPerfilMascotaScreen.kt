// Archivo: EditarPerfilMascotaScreen.kt
package com.example.petbook.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.petbook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun EditarPerfilMascotaScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }

    var contraActual by remember { mutableStateOf("") }
    var contraNueva by remember { mutableStateOf("") }
    var repetirContra by remember { mutableStateOf("") }

    var correoActual by remember { mutableStateOf("") }
    var correoNuevo by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        uri?.let {
            val uid = user?.uid
            if (uid != null) {
                val ref = storage.reference.child("mascotas/$uid.jpg")
                ref.putFile(it)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener { downloadUrl ->
                                db.collection("mascotas").whereEqualTo("correo", user.email).get()
                                    .addOnSuccessListener { result ->
                                        val docId = result.documents.firstOrNull()?.id
                                        if (docId != null) {
                                            db.collection("mascotas").document(docId)
                                                .update("fotoUrl", downloadUrl.toString())
                                                .addOnSuccessListener {
                                                    Toast.makeText(context, "Foto actualizada", Toast.LENGTH_SHORT).show()
                                                    fotoUrl = downloadUrl.toString()
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(context, "Error al guardar la foto", Toast.LENGTH_SHORT).show()
                                                }
                                        } else {
                                            Toast.makeText(context, "Documento no encontrado", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Error al buscar documento", Toast.LENGTH_SHORT).show()
                                    }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Error al obtener la URL de descarga", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al subir imagen: ${it.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }

    LaunchedEffect(Unit) {
        user?.email?.let { email ->
            db.collection("mascotas").whereEqualTo("correo", email).get()
                .addOnSuccessListener { result ->
                    for (doc in result) {
                        nombre = doc.getString("nombre") ?: ""
                        colonia = doc.getString("colonia") ?: ""
                        ciudad = "GDL"
                        edad = doc.getString("edad") ?: ""
                        peso = doc.getString("peso") ?: ""
                        raza = doc.getString("raza") ?: ""
                        correoActual = doc.getString("correo") ?: ""
                        fotoUrl = doc.getString("fotoUrl") ?: ""
                        break
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD4E4F4))
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Regresar",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { (context as? Activity)?.finish() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Image(painter = painterResource(id = R.drawable.logo_petbook), contentDescription = null, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text("EDITAR PERFIL", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF2C3E50))
        Spacer(modifier = Modifier.height(12.dp))

        when {
            imageUri != null -> Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier.size(140.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            fotoUrl.isNotBlank() -> Image(
                painter = rememberAsyncImagePainter(fotoUrl),
                contentDescription = null,
                modifier = Modifier.size(140.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            else -> Image(
                painter = painterResource(id = R.drawable.kero_selfie),
                contentDescription = null,
                modifier = Modifier.size(140.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Text("Cambiar foto de perfil", fontSize = 12.sp, color = Color.Blue, modifier = Modifier.clickable {
            galleryLauncher.launch("image/*")
        })

        Spacer(modifier = Modifier.height(16.dp))

        RoundedField("Nombre:", nombre) { nombre = it }
        RoundedField("Ciudad:", ciudad) { ciudad = it }
        RoundedField("Zona o Colonia:", colonia) { colonia = it }
        RoundedField("Edad:", edad, KeyboardType.Number) { edad = it }
        RoundedField("Peso:", peso, KeyboardType.Number) { peso = it }
        RoundedField("Raza:", raza) { raza = it }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Cambio de contraseña", fontWeight = FontWeight.Bold, color = Color(0xFF2C3E50))
        RoundedField("Contraseña actual:", contraActual, KeyboardType.Password, true) { contraActual = it }
        RoundedField("Contraseña nueva:", contraNueva, KeyboardType.Password, true) { contraNueva = it }
        RoundedField("Repetir nueva contraseña:", repetirContra, KeyboardType.Password, true) { repetirContra = it }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Cambio de correo electrónico", fontWeight = FontWeight.Bold, color = Color(0xFF2C3E50))
        RoundedField("Correo electrónico actual:", correoActual, KeyboardType.Email) { correoActual = it }
        RoundedField("Correo electrónico nuevo:", correoNuevo, KeyboardType.Email) { correoNuevo = it }

        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.btn_guardar),
            contentDescription = "Guardar cambios",
            modifier = Modifier
                .size(width = 220.dp, height = 50.dp)
                .clickable {
                    if (user?.email != null) {
                        db.collection("mascotas").whereEqualTo("correo", user.email).get()
                            .addOnSuccessListener { result ->
                                for (doc in result) {
                                    val docId = doc.id
                                    db.collection("mascotas").document(docId).update(
                                        mapOf(
                                            "nombre" to nombre,
                                            "colonia" to colonia,
                                            "edad" to edad,
                                            "peso" to peso,
                                            "raza" to raza,
                                            "correo" to if (correoNuevo.isNotBlank()) correoNuevo else correoActual
                                        )
                                    ).addOnSuccessListener {
                                        Toast.makeText(context, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show()
                                        (context as? Activity)?.finish()
                                    }.addOnFailureListener {
                                        Toast.makeText(context, "Error al guardar cambios", Toast.LENGTH_SHORT).show()
                                    }
                                    break
                                }
                            }
                    }
                }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
fun RoundedField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp)) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}
