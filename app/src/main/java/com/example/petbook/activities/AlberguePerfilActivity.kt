package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petbook.R
import com.example.petbook.components.PerfilAlbergueScreen
import com.example.petbook.components.PerfilClinicaScreen

class AlberguePerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerfilAlbergueScreen()  // Tu función @Composable
        }
    }
}