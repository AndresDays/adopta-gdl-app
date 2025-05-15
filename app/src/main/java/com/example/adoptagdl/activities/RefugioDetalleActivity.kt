package com.example.adoptagdl.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adoptagdl.screens.RefugioDetalleScreen

class RefugioDetalleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val refugioId = intent.getStringExtra("refugioId") ?: ""

        setContent {
            RefugioDetalleScreen(refugioId = refugioId)
        }
    }
}
