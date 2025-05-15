package com.example.adoptagdl.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.adoptagdl.R

@Composable
fun DonacionRealizadaScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_donacion_realizada),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_volver),
                contentDescription = "Volver",
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        (context as? Activity)?.finish()
                    }
            )
        }

        BottomNavigationBar(current = "search", modifier = Modifier.align(Alignment.BottomCenter))
    }
}
