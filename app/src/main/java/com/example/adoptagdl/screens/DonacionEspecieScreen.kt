package com.example.adoptagdl.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R

@Composable
fun DonacionEspecieScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_especie),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_volver),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(85.dp)
                        .clickable { (context as? Activity)?.finish() }
                )
            }
            Spacer(modifier = Modifier.height(500.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF5D1), shape = MaterialTheme.shapes.medium)
                    .padding(12.dp)
            ) {
                Text(
                    "Lunes a Domingo de 8:00 a.m. a 8 p.m.\nRefugio ‘Nuevo Hogar’, Colonia Americana #479"
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        BottomNavigationBar(current = "search", modifier = Modifier.align(Alignment.BottomCenter))
    }
}
