package com.example.adoptagdl.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adoptagdl.screens.RegistroVeterinariaScreen
import com.example.adoptagdl.activities.LoginActivity

class SignUpRefugioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistroVeterinariaScreen(
                onRegistroExitoso = {
                    // Aquí también puedes enviar a un feed o pantalla de bienvenida
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                onBack = {
                    // Volver al login
                    finish()
                }
            )
        }
    }
}
