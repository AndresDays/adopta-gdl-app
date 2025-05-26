package com.example.petbook.components

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.example.petbook.R
import com.example.petbook.activities.AlberguePerfilActivity
import com.example.petbook.activities.EditarPerfilClinicaActivity
import com.example.petbook.activities.EditarPerfilMascotaActivity
import com.example.petbook.activities.EditarPerfilRefugioActivity
import com.example.petbook.activities.FeedActivity
import com.example.petbook.activities.LoginActivity
import com.example.petbook.activities.ResenasActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email

    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var fotoUrl by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                email?.let {
                    db.collection("mascotas")
                        .whereEqualTo("correo", it)
                        .get()
                        .addOnSuccessListener { result ->
                            val document = result.documents.firstOrNull()
                            if (document != null) {
                                nombre = document.getString("nombre") ?: ""
                                edad = document.getString("edad") ?: ""
                                peso = document.getString("peso") ?: ""
                                raza = document.getString("raza") ?: ""
                                colonia = document.getString("colonia") ?: ""
                                fotoUrl = document.getString("fotoUrl") ?: ""
                            }
                        }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFCCE1F9))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Regresar",
                modifier = Modifier.align(Alignment.CenterStart).size(32.dp).clickable {
                    context.startActivity(Intent(context, FeedActivity::class.java))
                }
            )

            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(84.dp).align(Alignment.Center)
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", modifier = Modifier.size(32.dp).padding(horizontal = 4.dp))

                Box {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Configuración",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 4.dp)
                            .clickable { menuExpanded = true }
                    )

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(8.dp)
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_logout),
                                                contentDescription = "Logout",
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Cerrar sesión")
                                        }
                                    },
                                    onClick = {
                                        FirebaseAuth.getInstance().signOut()
                                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                                        context.startActivity(Intent(context, LoginActivity::class.java))
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Configuración") },
                                    onClick = {}
                                )
                                DropdownMenuItem(
                                    text = { Text("Privacidad") },
                                    onClick = {}
                                )
                            }
                        }
                    }
                }

                Image(painter = painterResource(id = R.drawable.ic_chat_bubble_outline), contentDescription = "Mensaje", modifier = Modifier.size(32.dp).padding(horizontal = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(160.dp)) {
                if (fotoUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoUrl),
                        contentDescription = "Foto Perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(160.dp).clip(CircleShape).scale(1.08f)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.kero_selfie),
                        contentDescription = "Foto Perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(160.dp).clip(CircleShape).scale(1.08f)
                    )
                }
                Box(modifier = Modifier.size(26.dp).align(Alignment.TopStart).offset(x = 10.dp, y = 4.dp).clip(CircleShape).background(Color(0xFFFFC6D8)))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("@$nombre", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF495C8E))
        Text("GDL", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(colonia, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileDataLine("Edad:", edad)
            ProfileDataLine("Peso:", "$peso kg")
            ProfileDataLine("Raza:", raza)

            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_editar),
                contentDescription = "Editar perfil",
                modifier = Modifier.width(180.dp).height(45.dp).clickable {
                    context.startActivity(Intent(context, EditarPerfilMascotaActivity::class.java))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_seguidores),
                contentDescription = "Seguidores perfil",
                modifier = Modifier.width(180.dp).height(45.dp).clickable {}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("\u2726 @$nombre actualizó su foto", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                if (fotoUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoUrl),
                        contentDescription = "Post",
                        modifier = Modifier.size(150.dp).clip(CircleShape).scale(1.1f),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.kero_selfie),
                        contentDescription = "Post",
                        modifier = Modifier.size(150.dp).clip(CircleShape).scale(1.1f),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Like")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(horizontal = 32.dp).fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("\u2726 @$nombre calificó una clínica veterinaria", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Veterinaria Droppy", color = Color(0xFF00B894), fontSize = 16.sp)
                Text("9.5", color = Color(0xFF00B894), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
fun ProfileDataLine(label: String, value: String) {
    Text(
        buildAnnotatedString {
            append("$label ")
            addStyle(SpanStyle(fontWeight = FontWeight.Bold), 0, label.length)
            append(value)
        },
        fontSize = 14.sp
    )
}

@Composable
fun PerfilClinicaScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            db.collection("clinicas").document(userId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        nombre = doc.getString("nombreEstablecimiento") ?: ""
                        direccion = doc.getString("direccion") ?: ""
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
            .background(Color(0xFFCCE1F9))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Regresar",
                modifier = Modifier.align(Alignment.CenterStart).size(32.dp).clickable {
                    context.startActivity(Intent(context, FeedActivity::class.java))
                }
            )

            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(84.dp).align(Alignment.Center)
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", modifier = Modifier.size(32.dp).padding(horizontal = 4.dp))

                Box {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Configuración",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 4.dp)
                            .clickable { menuExpanded = true }
                    )

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(8.dp)
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_logout),
                                                contentDescription = "Logout",
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Cerrar sesión")
                                        }
                                    },
                                    onClick = {
                                        FirebaseAuth.getInstance().signOut()
                                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                                        context.startActivity(Intent(context, LoginActivity::class.java))
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Configuración") },
                                    onClick = {}
                                )
                                DropdownMenuItem(
                                    text = { Text("Privacidad") },
                                    onClick = {}
                                )
                            }
                        }
                    }
                }

                Image(painter = painterResource(id = R.drawable.ic_chat_bubble_outline), contentDescription = "Mensaje", modifier = Modifier.size(32.dp).padding(horizontal = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_servicios),
            contentDescription = "Servicios",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 4.dp)
                .width(120.dp)
                .height(35.dp)
                .clickable { }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.vet1),
            contentDescription = "Foto clínica",
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_editar),
            contentDescription = "Editar perfil",
            modifier = Modifier
                .width(140.dp)
                .height(36.dp)
                .clickable { context.startActivity(Intent(context, EditarPerfilClinicaActivity::class.java))}
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(nombre, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF495C8E))
        Spacer(modifier = Modifier.height(1.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFF2C3E50))
            Text("GDL", fontWeight = FontWeight.Bold, color = Color(0xFF2C3E50))
        }
        Text(
            direccion,
            fontSize = 13.sp,
            color = Color(0xFF2C3E50),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            descripcion,
            fontSize = 12.sp,
            color = Color(0xFF2C3E50),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(horario, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(12.dp))

        // Gráfico Pie manual
        Canvas(modifier = Modifier.size(180.dp)) {
            val pieData = listOf(
                0.6f to Color(0xFF2ECC71), // Recomendable
                0.3f to Color(0xFFFFA500), // Puede mejorar
                0.1f to Color(0xFFE74C3C)  // No recomendable
            )
            var startAngle = -90f
            pieData.forEach { (percentage, color) ->
                val sweep = percentage * 360
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true
                )
                startAngle += sweep
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("RECOMENDABLE", color = Color(0xFF2ECC71), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text("PUEDE MEJORAR", color = Color(0xFFFFA500), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text("NO RECOMENDABLE", color = Color(0xFFE74C3C), fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_resenas),
            contentDescription = "Reseñas",
            modifier = Modifier
                .width(160.dp)
                .height(44.dp)
                .clickable { }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
fun PerfilAlbergueScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }
    var serviciosExpanded by remember { mutableStateOf(false) }

    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var nombre by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var servicios by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            db.collection("albergues").document(userId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        nombre = doc.getString("nombre") ?: ""
                        ubicacion = doc.getString("ubicacion") ?: ""
                        direccion = doc.getString("direccion") ?: ""
                        descripcion = doc.getString("descripcion") ?: ""
                        horario = doc.getString("horario") ?: ""
                        servicios = (doc.get("servicios") as? List<*>)?.mapNotNull { it?.toString() } ?: listOf()
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFCCE1F9))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Regresar",
                modifier = Modifier.align(Alignment.CenterStart).size(32.dp).clickable {
                    context.startActivity(Intent(context, FeedActivity::class.java))
                }
            )

            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(84.dp).align(Alignment.Center)
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", modifier = Modifier.size(32.dp).padding(horizontal = 4.dp))

                Box {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Configuración",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 4.dp)
                            .clickable { menuExpanded = true }
                    )

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "Logout", modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cerrar sesión")
                                }
                            },
                            onClick = {
                                FirebaseAuth.getInstance().signOut()
                                Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                                context.startActivity(Intent(context, LoginActivity::class.java))
                            }
                        )
                    }
                }

                Image(painter = painterResource(id = R.drawable.ic_chat_bubble_outline), contentDescription = "Mensaje", modifier = Modifier.size(32.dp).padding(horizontal = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Image(
            painter = painterResource(id = R.drawable.btn_servicios),
            contentDescription = "Servicios",
            modifier = Modifier
                .align(Alignment.End)
                .width(120.dp)
                .height(35.dp)
                .clickable { serviciosExpanded = true }
        )

        DropdownMenu(
            expanded = serviciosExpanded,
            onDismissRequest = { serviciosExpanded = false }
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Servicios ofrecidos:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                servicios.forEach {
                    Text(text = "• $it", fontSize = 15.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.albergue1),
            contentDescription = "Foto albergue",
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.btn_editar),
            contentDescription = "Editar perfil",
            modifier = Modifier
                .width(140.dp)
                .height(36.dp)
                .clickable {
                    context.startActivity(Intent(context, EditarPerfilRefugioActivity::class.java))
                }
        )
        Text(nombre, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF495C8E))
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFF2C3E50))
            Spacer(modifier = Modifier.width(4.dp))
            Text("GDL", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF2C3E50))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            ubicacion,
            fontSize = 18.sp,
            color = Color(0xFF2C3E50),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            descripcion,
            fontSize = 18.sp,
            color = Color(0xFF2C3E50),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            horario,
            fontSize = 18.sp,
            color = Color(0xFF2C3E50),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_donaciones),
            contentDescription = "Donaciones",
            modifier = Modifier
                .width(160.dp)
                .height(44.dp)
                .clickable { }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Canvas(modifier = Modifier.size(180.dp)) {
            val pieData = listOf(
                0.6f to Color(0xFF2ECC71),
                0.3f to Color(0xFFFFA500),
                0.1f to Color(0xFFE74C3C)
            )
            var startAngle = -90f
            pieData.forEach { (percentage, color) ->
                val sweep = percentage * 360
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true
                )
                startAngle += sweep
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("RECOMENDABLE", color = Color(0xFF2ECC71), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("PUEDE MEJORAR", color = Color(0xFFFFA500), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("NO RECOMENDABLE", color = Color(0xFFE74C3C), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.btn_resenas),
            contentDescription = "Reseñas",
            modifier = Modifier
                .width(160.dp)
                .height(44.dp)
                .clickable { context.startActivity(Intent(context, ResenasActivity::class.java))}
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}









