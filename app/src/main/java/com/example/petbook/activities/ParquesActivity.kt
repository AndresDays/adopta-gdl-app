package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.petbook.screens.ParquesScreen

class ParquesActivity : ComponentActivity() {
    companion object {
        var parqueSeleccionadoId: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ParquesScreen()

        }
    }
}

