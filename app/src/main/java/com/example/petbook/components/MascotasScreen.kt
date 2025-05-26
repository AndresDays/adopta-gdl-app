package com.example.petbook.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.FeedActivity

@Composable
fun MascotasScreen(onBack: () -> Unit) {
    val searchText = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB9D7EA))
            .verticalScroll(rememberScrollState())
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Fila superior con flecha y logo centrado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(30.dp)
                        .clickable {
                            context.startActivity(Intent(context, FeedActivity::class.java))
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.logo_petbook),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(90.dp)
                )
            }

            // Título
            Text(
                text = "¡Busca Mascotas en tu zona!",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF495C8E),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Lupa y campo de búsqueda
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lupa),
                    contentDescription = "Lupa",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { focusRequester.requestFocus() }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .height(48.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = searchText.value,
                        onValueChange = { searchText.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (searchText.value.isEmpty()) {
                                Text("Buscar...", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val mascotas = listOf(
                Triple("mascota1", "@hiro", R.drawable.simbolo_mujer),
                Triple("mascota2", "@keroppi", R.drawable.simbolo_hombre),
                Triple("mascota3", "@harry", R.drawable.simbolo_hombre),
                Triple("mascota4", "@luis", R.drawable.simbolo_hombre),
                Triple("mascota5", "@lolayloli", R.drawable.simbolo_mujer)
            )

            mascotas.forEach { (imagen, nombre, simbolo) ->
                MascotaCard(imagen, nombre, simbolo)
            }
        }
    }
}


@Composable
fun MascotaCard(imagen: String, nombre: String, simbolo: Int) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF5FD0D1), shape = CircleShape)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = getDrawableId(imagen)),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White, shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = nombre,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.btn_seguir),
                    contentDescription = "Seguir",
                    modifier = Modifier
                        .width(110.dp)
                        .height(35.dp)
                )
            }
        }

        // 🧩 Posición refinada del símbolo
        Image(
            painter = painterResource(id = simbolo),
            contentDescription = "Género",
            modifier = Modifier
                .size(42.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-16).dp, y = (-10).dp) // más hacia afuera y más esquinado
        )
    }
}





fun getDrawableId(name: String): Int {
    return when (name) {
        "mascota1" -> R.drawable.mascota1
        "mascota2" -> R.drawable.mascota2
        "mascota3" -> R.drawable.mascota3
        "mascota4" -> R.drawable.mascota4
        "mascota5" -> R.drawable.mascota5
        else -> R.drawable.mascota1
    }
}
