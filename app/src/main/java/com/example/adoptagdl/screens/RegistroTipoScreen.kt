package com.example.adoptagdl.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R

@Composable
fun RegistroTipoScreen(
    onUsuarioClick: () -> Unit,
    onVeterinariaClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo completo con curva blanca inferior y título
        Image(
            painter = painterResource(id = R.drawable.crear_cuenta),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 220.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón ADOPTANTE (más grande)
            Image(
                painter = painterResource(id = R.drawable.btn_adoptante),
                contentDescription = "Botón Adoptante",
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Aumentado
                    .height(190.dp)     // Aumentado
                    .clickable { onUsuarioClick() }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón REFUGIO/ALBERGUE (más grande)
            Image(
                painter = painterResource(id = R.drawable.btn_refugio_albergue),
                contentDescription = "Botón Refugio",
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Aumentado
                    .height(190.dp)     // Aumentado
                    .clickable { onVeterinariaClick() }
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja hacia abajo

            // Texto justo donde termina el fondo verde
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INICIA SESIÓN",
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }
    }
}
