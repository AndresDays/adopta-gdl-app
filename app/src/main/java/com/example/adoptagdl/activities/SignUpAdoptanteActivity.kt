package com.example.adoptagdl.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adoptagdl.screens.RegistroAdoptanteScreen
import com.example.adoptagdl.activities.LoginActivity

class SignUpAdoptanteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistroAdoptanteScreen(
                onRegistroExitoso = {
                    // Aquí podrías redirigir a otra pantalla (ej. Feed, Home...)
                    // Por ahora, regresamos al login
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                onBack = {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            )
        }
    }
}
