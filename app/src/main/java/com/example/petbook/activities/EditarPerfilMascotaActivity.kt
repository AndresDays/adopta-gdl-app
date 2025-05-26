package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.petbook.components.EditarPerfilMascotaScreen
import com.example.petbook.ui.theme.PetBookTheme

class EditarPerfilMascotaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme(darkTheme = false, dynamicColor = false) {
                EditarPerfilMascotaScreen()
            }
        }
    }
}
