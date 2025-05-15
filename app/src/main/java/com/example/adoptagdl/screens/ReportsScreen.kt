package com.example.adoptagdl.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.screens.BottomNavigationBar
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CampoTexto(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDEF8E4), shape = RoundedCornerShape(12.dp)),
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFDEF8E4),
                focusedContainerColor = Color(0xFFDEF8E4),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(lineHeight = 20.sp),
            minLines = if (singleLine) 1 else 5
        )
    }
}

@Composable
fun ReportsScreen() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var ubicacion by remember { mutableStateOf("") }
    var animal by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.reporte_maltrato),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(80.dp) // Área táctil más grande
                .background(Color.Transparent)
                .clickable { context.startActivity(Intent(context, FeedActivity::class.java)) } // Toda esta área será clickeable
                .align(Alignment.TopStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_volver),
                contentDescription = "Volver",
                modifier = Modifier.fillMaxSize() // La imagen se expande al Box
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            CampoTexto(label = "Ubicación", value = ubicacion, onValueChange = { ubicacion = it })
            Spacer(modifier = Modifier.height(12.dp))
            CampoTexto(label = "Animal", value = animal, onValueChange = { animal = it })
            Spacer(modifier = Modifier.height(12.dp))
            CampoTexto(
                label = "Descripción",
                value = descripcion,
                onValueChange = { descripcion = it },
                singleLine = false
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (ubicacion.isBlank() || animal.isBlank() || descripcion.isBlank()) {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val datos = hashMapOf(
                        "ubicacion" to ubicacion,
                        "animal" to animal,
                        "descripcion" to descripcion
                    )
                    db.collection("reportes").add(datos).addOnSuccessListener {
                        Toast.makeText(context, "Reporte enviado con éxito", Toast.LENGTH_SHORT).show()
                        ubicacion = ""
                        animal = ""
                        descripcion = ""
                    }.addOnFailureListener {
                        Toast.makeText(context, "Error al enviar reporte", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("HACER REPORTE", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            val contactoTexto = buildAnnotatedString {
                append("o contacta directamente al\n01 800 - ayudaanimal\no en el sitio web\n")
                pushStringAnnotation(tag = "URL", annotation = "https://rescatandoanimales.com.mx")
                pushStyle(
                    SpanStyle(
                        color = Color(0xFF2196F3),
                        textDecoration = TextDecoration.Underline
                    )
                )
                append("rescatandoanimales.com.mx")
                pop()
                pop()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                ClickableText(
                    text = contactoTexto,
                    style = LocalTextStyle.current.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    ),
                    onClick = { offset ->
                        contactoTexto.getStringAnnotations(tag = "URL", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                                context.startActivity(intent)
                            }
                    }
                )
            }
        }

        BottomNavigationBar(
            current = "home",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
