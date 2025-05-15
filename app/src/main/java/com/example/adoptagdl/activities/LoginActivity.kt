package com.example.adoptagdl.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        enableEdgeToEdge()
        setContent {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.iniciar_sesion),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(250.dp))

                    Text(
                        text = "Usuario",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFD7EBFF),
                            focusedContainerColor = Color(0xFFD7EBFF),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Contraseña",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, bottom = 4.dp),
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFD7EBFF),
                            focusedContainerColor = Color(0xFFD7EBFF),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.btn_listo_azul),
                        contentDescription = "Botón Listo",
                        modifier = Modifier
                            .height(48.dp)
                            .clickable {
                                if (email.isEmpty()) {
                                    Toast.makeText(this@LoginActivity, "Usuario vacío", Toast.LENGTH_SHORT).show()
                                    return@clickable
                                }
                                if (password.isEmpty()) {
                                    Toast.makeText(this@LoginActivity, "Contraseña vacía", Toast.LENGTH_SHORT).show()
                                    return@clickable
                                }
                                doLogin(email, password)
                            }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("¿Aún no tienes una cuenta?")
                    Text(
                        text = "CREA UNA CUENTA",
                        color = Color(0xFF64B5F6),
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            startActivity(Intent(this@LoginActivity, RegisterTypeActivity::class.java))
                        }
                    )
                }
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        loginFirebase(auth, email, password, onFail = {
            Toast.makeText(this, "El usuario no existe o sus credenciales son incorrectas", Toast.LENGTH_SHORT).show()
        }) { user ->
            getDocument(db, "users", user.uid, { _ ->
                storeDocument(
                    db,
                    "users",
                    user.uid,
                    hashMapOf("onboardingStatus" to OnboardingStatus.NOT_STARTED),
                    {
                        goToNextScreen()
                    },
                    { errorMessage ->
                        Toast.makeText(this, "Error Firestore: $errorMessage", Toast.LENGTH_LONG).show()
                    })
            }) {
                goToNextScreen()
            }
        }
    }

    private fun goToNextScreen() {
        val uid = auth.currentUser?.uid ?: return

        getDocument(db, "users", uid,
            onFail = {
                startActivity(Intent(this, FeedActivity::class.java))
                finish()
            },
            onSuccess = { userData ->
                val onboardingStatus = userData["onboardingStatus"]?.toString()
                if (onboardingStatus == "COMPLETED") {
                    startActivity(Intent(this, FeedActivity::class.java))
                } else {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
                finish()
            }
        )
    }

}
