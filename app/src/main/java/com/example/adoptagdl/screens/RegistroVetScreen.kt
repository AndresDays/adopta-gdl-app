package com.example.adoptagdl.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.repositorio.FirebaseRepository

@Composable
fun RegistroVeterinariaScreen(
    onRegistroExitoso: () -> Unit,
    onBack: () -> Unit
) {
    var nombreVet by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.albergue_refugio),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(80.dp) // Área táctil más grande
                .background(Color.Transparent)
                .clickable { onBack() } // Toda esta área será clickeable
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
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(220.dp))

            FormFields(label = "Nombre del refugio", value = nombreVet, onValueChange = { nombreVet = it })
            Spacer(modifier = Modifier.height(20.dp))

            FormFields(label = "Dirección", value = direccion, onValueChange = { direccion = it })
            Spacer(modifier = Modifier.height(20.dp))

            FormFields(label = "Contacto", value = contacto, onValueChange = { contacto = it })
            Spacer(modifier = Modifier.height(20.dp))

            FormFields(label = "Correo", value = correo, onValueChange = { correo = it })
            Spacer(modifier = Modifier.height(20.dp))

            FormFields(label = "Contraseña", value = contrasena, isPassword = true, onValueChange = { contrasena = it })
            Spacer(modifier = Modifier.height(20.dp))

            mensajeError?.let {
                Text(text = it, color = Color.Red)
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Botón LISTO como imagen
            Image(
                painter = painterResource(id = R.drawable.btn_listo_verde),
                contentDescription = "Botón Listo",
                modifier = Modifier
                    .height(55.dp)
                    .clickable {
                        FirebaseRepository.registrarRefugio(
                            nombreVet, direccion, contacto, correo, contrasena
                        ) { success, error ->
                            if (success) {
                                onRegistroExitoso()
                            } else {
                                mensajeError = error
                            }
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto inferior
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "INICIA SESIÓN",
                    color = Color(0xFF64B5F6),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun FormFields(
    label: String,
    value: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFDEF8E4),
                focusedContainerColor = Color(0xFFDEF8E4),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
    }
}
