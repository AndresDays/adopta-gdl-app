package com.example.adoptagdl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Puedes agregar un pequeño delay si lo deseas
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
