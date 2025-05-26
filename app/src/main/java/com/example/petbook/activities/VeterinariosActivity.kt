package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.petbook.components.VeterinariosScreen


class VeterinariosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VeterinariosScreen(onBack = { finish() })
        }
    }
}
