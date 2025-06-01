package com.example.petbook.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.AgregarResenaActivity
import com.example.petbook.activities.ResenasActivity
import com.example.petbook.activities.VeterinariosActivity
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ClinicasPerfilGeneralScreen(clinicaId: String) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    var serviciosExpanded by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var servicios by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(clinicaId) {
        if (clinicaId.isNotBlank()) {
            db.collection("clinicas").document(clinicaId).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        nombre = doc.getString("nombreEstablecimiento") ?: ""
                        direccion = doc.getString("direccion") ?: ""
                        descripcion = doc.getString("descripcion") ?: ""
                        horario = doc.getString("horario") ?: ""
                        telefono = doc.getString("telefono") ?: ""
                        servicios = (doc.get("servicios") as? List<*>)?.mapNotNull { it?.toString() } ?: listOf()
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFCCE1F9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp).clickable {
                    context.startActivity(Intent(context, VeterinariosActivity::class.java))
                }
            )
            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp)
            )
            Text(telefono, color = Color.Black, fontSize = 14.sp, textDecoration = TextDecoration.Underline)
        }

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

        Spacer(modifier = Modifier.height(12.dp))
        Image(painter = painterResource(id = R.drawable.vet1), contentDescription = null, modifier = Modifier.size(140.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF495C8E))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(4.dp))
            Text("GDL", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Image(
                painter = painterResource(id = R.drawable.btn_calificar),
                contentDescription = null,
                modifier = Modifier.size(width = 130.dp, height = 45.dp)
                    .clickable {
                        val intent = Intent(context, AgregarResenaActivity::class.java)
                        intent.putExtra("clinicaId", clinicaId)
                        context.startActivity(intent)
                    }

            )
            Image(
                painter = painterResource(id = R.drawable.btn_mensaje),
                contentDescription = null,
                modifier = Modifier.size(width = 180.dp, height = 80.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(direccion, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(descripcion, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp))
        Text(horario, fontWeight = FontWeight.Bold, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))
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
            Text("RECOMENDABLE", color = Color(0xFF2ECC71), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text("PUEDE MEJORAR", color = Color(0xFFFFA500), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text("NO RECOMENDABLE", color = Color(0xFFE74C3C), fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Image(
                painter = painterResource(id = R.drawable.btn_resenas),
                contentDescription = null,
                modifier = Modifier.size(width = 140.dp, height = 45.dp)
                    .clickable {
                        context.getSharedPreferences("PetBookPrefs", Context.MODE_PRIVATE)
                            .edit()
                            .putString("clinicaActualId", clinicaId)
                            .apply()
                        context.startActivity(Intent(context, ResenasActivity::class.java))
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.btn_cedulas),
                contentDescription = null,
                modifier = Modifier.size(width = 180.dp, height = 60.dp)
            )
        }
    }
}