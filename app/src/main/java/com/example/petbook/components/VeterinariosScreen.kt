package com.example.petbook.components

import android.content.Intent
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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import com.google.firebase.firestore.FirebaseFirestore

data class Clinica(
    val nombreEstablecimiento: String = "",
    val direccion: String = ""
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VeterinariosScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var searchText by remember { mutableStateOf("") }

    var clinicas by remember { mutableStateOf(listOf<Clinica>()) }

    // Carga los datos desde Firebase
    LaunchedEffect(Unit) {
        FirebaseFirestore.getInstance().collection("clinicas")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.mapNotNull { it.toObject(Clinica::class.java) }
                clinicas = lista
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD1E6F9))
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Flecha y logo centrado
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(36.dp)
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

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Busca Veterinarios en tu zona!",
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

        val clinicasFiltradas = clinicas.filter {
            it.nombreEstablecimiento.contains(searchText, ignoreCase = true) ||
                    it.direccion.contains(searchText, ignoreCase = true)
        }

        clinicasFiltradas.forEach {
            VeterinariaCard(it.nombreEstablecimiento, it.direccion)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun VeterinariaCard(nombre: String, direccion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.vet1),
            contentDescription = nombre,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column {
            Text(
                text = nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = direccion,
                fontSize = 16.sp
            )
        }
    }
}
