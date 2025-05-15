package com.example.adoptagdl.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.adoptagdl.screens.AdoptanteProfileScreen
import com.example.adoptagdl.screens.RefugioProfileScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var userType by remember { mutableStateOf<String?>(null) }
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val db = FirebaseFirestore.getInstance()

            LaunchedEffect(uid) {
                uid?.let {
                    db.collection("adoptantes").document(it).get()
                        .addOnSuccessListener { adoptanteDoc ->
                            if (adoptanteDoc.exists()) {
                                userType = "adoptante"
                            } else {
                                db.collection("refugios").document(it).get()
                                    .addOnSuccessListener { refugioDoc ->
                                        userType = if (refugioDoc.exists()) "refugio" else "unknown"
                                    }
                            }
                        }
                } ?: run {
                    userType = "unknown"
                }
            }

            when (userType) {
                "adoptante" -> AdoptanteProfileScreen()
                "refugio" -> RefugioProfileScreen()
                "unknown" -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontró el tipo de usuario.")
                }
                else -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                }
            }
        }
    }
}
