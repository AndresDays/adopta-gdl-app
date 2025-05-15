// ReporteMaltratoActivity.kt
package com.example.adoptagdl.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ReportsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReportsScreen()
        }
    }
}