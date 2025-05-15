package com.example.adoptagdl.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R

@Composable
fun InboxScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_rosa),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp),
        ) {
            Text(
                text = "BANDEJA DE ENTRADA",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NOTIFICACIONES",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )

            Spacer(modifier = Modifier.height(24.dp))

            NotificationBox("‘Nuevo Hogar’ ha agregado una nueva mascota")
            NotificationBox("¡LOKI’ ha sido adoptado!")
            NotificationBox("Tu solicitud de reporte ha sido atendida.")
        }

        BottomNavigationBar(
            current = "inbox",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun NotificationBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFCEBED), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .padding(vertical = 5.dp)
            .padding(bottom = 2.dp)
            .wrapContentHeight()
    ) {
        Text(text = text, fontSize = 14.sp, color = Color.Black)
    }

    Spacer(modifier = Modifier.height(16.dp))
}
