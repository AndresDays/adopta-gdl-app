package com.example.adoptagdl.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.adoptagdl.screens.DonacionMonetariaScreen

class DonacionMonetariaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DonacionMonetariaScreen()
        }
    }
}
