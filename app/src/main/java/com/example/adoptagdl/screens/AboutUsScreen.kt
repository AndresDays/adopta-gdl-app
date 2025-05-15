package com.example.adoptagdl.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R

@Composable
fun AboutUsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_azul),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CONÓCENOS",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    color = Color(0xFF212121)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo_app),
                    contentDescription = "Logo",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = """
En AdoptaGDL, creemos que cada mascota merece un hogar lleno de amor y cuidados. Nuestra misión es conectar a los peluditos que buscan una familia con personas responsables y comprometidas con la adopción.

Sabemos que miles de perros y gatos son abandonados cada año, y queremos ser parte del cambio. Por eso, hemos creado esta plataforma para facilitar la adopción responsable, brindando herramientas que ayuden a encontrar la mejor combinación entre adoptantes y mascotas.

A través de AdoptaGDL, los refugios y albergues pueden dar mayor visibilidad a los animales que necesitan un hogar, mientras que los adoptantes pueden explorar perfiles, conocer sus historias y darles una segunda oportunidad.

Si no puedes adoptar, también puedes apoyar con donaciones para mejorar la calidad de vida de los animalitos en refugios. Porque ayudar es más que una acción, es un compromiso con quienes más nos necesitan.

¡Juntos podemos hacer la diferencia! 🐾❤️🔥
                    """.trimIndent(),
                    fontSize = 18.sp,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }

        BottomNavigationBar(
            current = "home",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
