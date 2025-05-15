package com.example.adoptagdl.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adoptagdl.screens.FormularioRefugioScreen

class FormularioRefugioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioRefugioScreen(onBack = { finish() })
        }
    }
}
