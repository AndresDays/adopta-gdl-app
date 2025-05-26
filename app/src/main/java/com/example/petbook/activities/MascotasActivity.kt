package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.petbook.ui.theme.PetBookTheme
import com.example.petbook.screens.MascotasScreen

class MascotasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetBookTheme {
                MascotasScreen(onBack = { finish() })
            }
        }
    }
}
