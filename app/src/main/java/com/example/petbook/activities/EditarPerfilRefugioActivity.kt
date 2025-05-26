package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petbook.R
import com.example.petbook.screens.EditarPerfilRefugioScreen
import com.example.petbook.ui.theme.PetBookTheme

class EditarPerfilRefugioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditarPerfilRefugioScreen(onBack = { finish() })
        }
    }
}