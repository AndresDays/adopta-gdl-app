package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petbook.R
import com.example.petbook.components.PostCard
import com.example.petbook.components.PostCardWithDrawer
import com.example.petbook.screens.ResenasScreen

class ResenasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ResenasScreen()
        }
    }
}