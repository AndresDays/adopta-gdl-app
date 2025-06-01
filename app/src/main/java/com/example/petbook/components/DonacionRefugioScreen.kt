package com.example.petbook.components

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
@Composable
fun DonacionRefugioScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFFBFD9EE))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { (context as? Activity)?.finish() }
            )

            Text("33 3966 4304", fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_petbook),
            contentDescription = "Logo",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Huellitas Libres", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color(0xFF495C8E))

        Spacer(modifier = Modifier.height(20.dp))

        Text("\u00bfCómo donar?", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(id = R.drawable.tarjeta),
            contentDescription = "Tarjeta",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Número de cuenta", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nombre: Refugio Huellitas Libres", fontWeight = FontWeight.Bold, fontSize = 15.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Todas las donaciones realizadas son para fines caritativos: alimento para mascotas, camas y construcción de mejores espacios recreativos para animales en situación de calle, hasta que estos consigan un cálido hogar.",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "\u00a1Gracias por apoyar una buena causa! Y no olvides promover la adopción, todos los animales merecen un techo donde vivir y una familia.",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2C3E50)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "El 2% de cada una de las donaciones se dirige a la plataforma de PetBook para el mantenimiento de la aplicación.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}
