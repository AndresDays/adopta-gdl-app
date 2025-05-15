package com.example.adoptagdl.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.adoptagdl.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val slides = listOf(
                R.drawable.slider1,
                R.drawable.slider2,
                R.drawable.slider3,
                R.drawable.slider4
            )
            var currentPage by remember { mutableStateOf(0) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount < -40 && currentPage < slides.lastIndex) {
                                currentPage++
                            } else if (dragAmount > 40 && currentPage > 0) {
                                currentPage--
                            }
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = slides[currentPage]),
                    contentDescription = "Slide $currentPage",
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_siguiente),
                        contentDescription = "Siguiente",
                        modifier = Modifier
                            .height(48.dp)
                            .clickable {
                                if (currentPage < slides.lastIndex) {
                                    currentPage++
                                } else {
                                    marcarOnboardingComoCompletado()
                                }
                            }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.btn_omitir),
                        contentDescription = "Omitir",
                        modifier = Modifier
                            .height(40.dp)
                            .clickable {
                                marcarOnboardingComoCompletado()
                            }
                    )
                }
            }
        }
    }

    private fun marcarOnboardingComoCompletado() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .update("onboardingStatus", "COMPLETED")
                .addOnSuccessListener {
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                }
        } else {
            startActivity(Intent(this, FeedActivity::class.java))
            finish()
        }
    }
}
