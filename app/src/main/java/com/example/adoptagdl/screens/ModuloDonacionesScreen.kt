package com.example.adoptagdl.screens

import android.app.Activity
import android.content.Intent
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
import com.example.adoptagdl.activities.DonacionEspecieActivity
import com.example.adoptagdl.activities.DonacionMonetariaActivity
import com.example.adoptagdl.activities.ModuloDonacionesActivity

@Composable
fun ModuloDonacionesScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_modulo_donacion),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_volver_verde),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { (context as? Activity)?.finish() }
                )
            }

            Spacer(modifier = Modifier.height(250.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_donacion_monetaria),
                contentDescription = "Donación monetaria",
                modifier = Modifier
                    .height(110.dp)
                    .fillMaxWidth(0.9f)
                    .clickable {context.startActivity(Intent(context, DonacionMonetariaActivity::class.java)) }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_donacion_especie),
                contentDescription = "Donación en especie",
                modifier = Modifier
                    .height(110.dp)
                    .fillMaxWidth(0.9f)
                    .clickable { context.startActivity(Intent(context, DonacionEspecieActivity::class.java))}
            )
        }

        BottomNavigationBar(current = "search", modifier = Modifier.align(Alignment.BottomCenter))
    }
}
