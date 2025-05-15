package com.example.adoptagdl.screens

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.activities.AboutUsActivity
import com.example.adoptagdl.activities.EditarPerfilAdoptanteActivity
import com.example.adoptagdl.util.getDocument
import com.example.adoptagdl.activities.FormularioRefugioActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AdoptanteProfileScreen() {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var mascotaDeseada by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(true) {
        if (userId != null) {
            getDocument(
                db = db,
                path = "adoptantes",
                id = userId,
                onFail = { },
                onSuccess = { data ->
                    nombre = data["nombre"]?.toString() ?: ""
                    telefono = data["telefono"]?.toString() ?: ""
                    correo = data["correo"]?.toString() ?: ""
                    colonia = data["colonia"]?.toString() ?: ""
                    mascotaDeseada = data["tipoMascota"]?.toString() ?: ""
                }
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_azul),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))

            Image(
                painter = painterResource(id = R.drawable.fotoyo3),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_editar_azul),
                contentDescription = "Editar Perfil",
                modifier = Modifier
                    .height(40.dp)
                    .clickable {
                        context.startActivity(Intent(context, EditarPerfilAdoptanteActivity::class.java))
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            InfoSection("Contacto", telefono, correo)
            Spacer(modifier = Modifier.height(20.dp))
            InfoSection("Colonia", colonia)
            Spacer(modifier = Modifier.height(20.dp))
            InfoSection("Mi mascota deseada es:", mascotaDeseada)
        }

        BottomNavigationBar(
            current = "profile",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun InfoSection(titulo: String, vararg valores: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        valores.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFB6D7F9), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(text = it)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RefugioProfileScreen() {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(true) {
        if (userId != null) {
            getDocument(
                db = db,
                path = "refugios",
                id = userId,
                onFail = { },
                onSuccess = { data ->
                    nombre = data["nombreRefugio"]?.toString() ?: ""
                    telefono = data["contacto"]?.toString() ?: ""
                    correo = data["correo"]?.toString() ?: ""
                    direccion = data["direccion"]?.toString() ?: ""
                }
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_verde),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.refugio1),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
            )

            Text(
                text = nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_editar_perfil),
                contentDescription = "Editar Perfil",
                modifier = Modifier
                    .height(40.dp)
                    .clickable {
                        context.startActivity(Intent(context, FormularioRefugioActivity::class.java))
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.imagen_refugio),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.imagen_refugio2),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.btn_agregar),
                    contentDescription = "Agregar Imagen",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            InfoRefugio(label = "Contacto", value = telefono)
            Spacer(modifier = Modifier.height(16.dp))
            InfoRefugio(label = "Correo", value = correo)
            Spacer(modifier = Modifier.height(16.dp))
            InfoRefugio(label = "Ubicación", value = direccion)
        }

        BottomNavigationBar(
            current = "profile",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun InfoRefugio(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDEF8E4), shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
