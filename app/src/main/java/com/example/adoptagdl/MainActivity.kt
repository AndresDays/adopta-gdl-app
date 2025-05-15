package com.example.adoptagdl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.adoptagdl.activities.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lanzar LoginActivity y cerrar esta
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
