// Archivo: ParquesScreen.kt
package com.example.petbook.screens

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.activities.FeedActivity
import com.example.petbook.activities.ParqueActivity
import com.example.petbook.activities.ParquesActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URL

// Data class para representar un parque
data class Parque(
    val id: String = "",
    val nombre: String = "",
    val direccion: String = "",
    val horario: String = "",
    val gusta: Int = 0,
    val imagenes: List<String> = emptyList()
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ParquesScreen(onBack: () -> Unit = {}) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    var parques by remember { mutableStateOf(listOf<Parque>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val result = db.collection("parques").get().await()
        val listaParques = result.mapNotNull { doc ->
            doc.toObject(Parque::class.java).copy(id = doc.id)
        }
        parques = listaParques
        isLoading = false
    }

    val filteredParques = parques.filter {
        it.nombre.contains(searchText, ignoreCase = true) ||
                it.direccion.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD1E6F9))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        context.startActivity(Intent(context, FeedActivity::class.java))
                    }
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(32.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Busca Parques en tu zona!",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color(0xFF2C3E50)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.lupa),
                contentDescription = "Buscar",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { focusRequester.requestFocus() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                textStyle = TextStyle(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            filteredParques.forEach { parque ->
                ParqueCard(parque = parque)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ParqueCard(parque: Parque) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(parque.imagenes) {
        bitmap = parque.imagenes.firstOrNull()?.let { downloadBitmap(it) }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                ParquesActivity.parqueSeleccionadoId = parque.id
                context.startActivity(Intent(context, ParqueActivity::class.java))
            }
            .padding(vertical = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = parque.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.parque1),
            contentDescription = "Default image",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(24.dp))

        Column {
            Text(
                text = parque.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = parque.direccion,
                fontSize = 14.sp
            )
        }
    }
}

suspend fun downloadBitmap(url: String): Bitmap? = withContext(Dispatchers.IO) {
    try {
        val input = URL(url).openStream()
        BitmapFactory.decodeStream(input)
    } catch (e: Exception) {
        null
    }
}
