package com.example.petbook.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.components.FormField
import com.example.petbook.ui.theme.PetBookTheme
import com.example.petbook.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : ComponentActivity() {

    private enum class AuthMode {
        SIGN_UP, LOG_IN
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        enableEdgeToEdge()
        setContent {
            var authMode by remember { mutableStateOf(AuthMode.LOG_IN) }

            PetBookTheme(darkTheme = false, dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (authMode == AuthMode.SIGN_UP) {
                        SeleccionTipoCuentaScreen(
                            onCrearCuenta = { tipoCuenta ->
                                when (tipoCuenta) {
                                    "mascota" -> startActivity(Intent(this, RegistroMascotaActivity::class.java))
                                    "clinica" -> startActivity(Intent(this, RegistroClinicaActivity::class.java))
                                    "albergue" -> startActivity(Intent(this, RegistroAlbergueActivity::class.java))
                                    else -> Toast.makeText(this, "Tipo de cuenta no implementado", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onIniciarSesion = {
                                authMode = AuthMode.LOG_IN
                            }
                        )
                    } else {
                        Surface(
                            modifier = Modifier
                                .padding(top = 28.dp)
                                .padding(top = innerPadding.calculateTopPadding()),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 48.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.petbook_logo),
                                        contentDescription = "petbook_logo",
                                        modifier = Modifier.size(width = 250.dp, height = 92.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.logo_petbook),
                                        contentDescription = "app_logo",
                                        modifier = Modifier.size(100.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    ElevatedButton(
                                        onClick = {
                                            authMode = AuthMode.SIGN_UP
                                        },
                                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text("REGISTRARME", fontWeight = FontWeight.Bold)
                                    }
                                }

                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 16.dp),
                                    shape = RoundedCornerShape(topStart = 64.dp),
                                    color = MaterialTheme.colorScheme.primary
                                ) {
                                    val formFieldModifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp)
                                    LoginForm(formFieldModifier)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SeleccionTipoCuentaScreen(onCrearCuenta: (String) -> Unit, onIniciarSesion: () -> Unit) {
        var selectedTipo by remember { mutableStateOf<String?>(null) }
        val context = LocalContext.current

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFFBFD9EE)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.petbook_logo), contentDescription = "petbook_logo")
                    Spacer(modifier = Modifier.height(12.dp))
                    Image(painter = painterResource(id = R.drawable.logo_petbook), contentDescription = "huella", modifier = Modifier.size(100.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_warning),
                            contentDescription = "Advertencia",
                            modifier = Modifier.size(36.dp).padding(end = 8.dp)
                        )
                        Text(
                            text = "Todas las cuentas de Organizaciones/\nAlbergues o Clínicas Veterinarias deben\nestar regulados por COFEPRIS y la Ley de\nProtección y Bienestar Animal.",
                            color = Color(0xFF2C4D7E),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF67D2D2)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "Quiero una cuenta:",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    listOf(
                        "mascota" to "Para mi Mascota",
                        "clinica" to "Administrativa (Clínica)",
                        "albergue" to "Administrativa (Albergue)"
                    ).forEach { (tipo, label) ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = selectedTipo == tipo,
                                onCheckedChange = { selectedTipo = tipo },
                                colors = CheckboxDefaults.colors(checkedColor = Color.White, uncheckedColor = Color.White)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(label, color = Color.White, fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = R.drawable.btn_comenzar_cuenta),
                        contentDescription = "Comenzar",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(width = 280.dp, height = 60.dp)
                            .clickable {
                                selectedTipo?.let { onCrearCuenta(it) }
                                    ?: Toast.makeText(context, "Selecciona un tipo de cuenta", Toast.LENGTH_SHORT).show()
                            }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "si ya tienes una cuenta",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "INICIA SESIÓN",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { onIniciarSesion() },
                        color = Color(0xFF2C4D7E),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    @Composable
    private fun LoginForm(modifier: Modifier) {
        val emailTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val passwordTextFieldState by remember { mutableStateOf(TextFieldState()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Correo electrónico:",
                    modifier = modifier,
                    inputType = KeyboardType.Email,
                    textFieldState = emailTextFieldState
                )
            }
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Introduzca su contraseña:",
                    modifier = modifier,
                    inputType = KeyboardType.Password,
                    textFieldState = passwordTextFieldState,
                    isPassword = true
                )
            }
            Image(
                painter = painterResource(id = R.drawable.btn_login),
                contentDescription = "Iniciar sesión",
                modifier = Modifier
                    .size(width = 280.dp, height = 95.dp)
                    .clickable {
                        if (emailTextFieldState.text.isEmpty()) {
                            Toast.makeText(
                                this@LoginActivity,
                                "El correo no puede estar vacío",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@clickable
                        }
                        if (passwordTextFieldState.text.isEmpty()) {
                            Toast.makeText(
                                this@LoginActivity,
                                "La contraseña no puede estar vacía",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@clickable
                        }
                        doLogin(
                            emailTextFieldState.text.toString(),
                            passwordTextFieldState.text.toString()
                        )
                    }
            )
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "¿Problemas para iniciar sesión?")
                Button(onClick = {
                    if (emailTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Introduzca un correo electrónico para recuperar su contraseña",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    passwordReset(
                        auth, emailTextFieldState.text.toString(),
                        onEmailSent = {
                            Toast.makeText(
                                this@LoginActivity,
                                "Correo enviado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFail = { exception ->
                            Toast.makeText(
                                this@LoginActivity,
                                "Ha ocurrido un error al enviar el correo de recuperación: $exception",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    )
                }) {
                    Text(text = "Olvidé mi contraseña")
                }
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = result.user
                if (user != null) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Usuario nulo", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al iniciar sesión: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
