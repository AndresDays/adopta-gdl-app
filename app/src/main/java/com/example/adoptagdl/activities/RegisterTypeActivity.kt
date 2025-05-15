package com.example.adoptagdl.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adoptagdl.screens.RegistroTipoScreen

class RegisterTypeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistroTipoScreen(
                onUsuarioClick = {
                    // Reemplaza con tu activity real
                    startActivity(Intent(this, SignUpAdoptanteActivity::class.java))
                },
                onVeterinariaClick = {
                    // Reemplaza con tu activity real
                    startActivity(Intent(this, SignUpRefugioActivity::class.java))
                },
                onLoginClick = {
                    // Regresa al login
                    finish()
                }
            )
        }
    }
}
