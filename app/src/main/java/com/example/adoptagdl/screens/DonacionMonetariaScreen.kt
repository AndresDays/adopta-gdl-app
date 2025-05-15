package com.example.adoptagdl.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.activities.DonacionMonetariaActivity
import com.example.adoptagdl.activities.DonacionRealizadaActivity

@Composable
fun DonacionMonetariaScreen() {
    val context = LocalContext.current
    var cantidad by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_donacion_monetaria),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_volver),
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(85.dp)
                        .clickable {
                            (context as? Activity)?.finish()
                        }
                )
            }

            Spacer(modifier = Modifier.height(140.dp))

            Text(
                text = "Cantidad de dinero que deseas donar:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(Color(0xFFFFF5CD), RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                BasicTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Método de pago:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            PaymentButton(R.drawable.btn_tarjeta_debito)
            Spacer(modifier = Modifier.height(12.dp))
            PaymentButton(R.drawable.btn_tarjeta_credito)
            Spacer(modifier = Modifier.height(12.dp))
            PaymentButton(R.drawable.btn_oxxopay)
            Spacer(modifier = Modifier.height(12.dp))
            PaymentButton(R.drawable.btn_paypal)
            Spacer(modifier = Modifier.height(12.dp))
            PaymentButton(R.drawable.btn_transferencia)
        }

        BottomNavigationBar(current = "search", modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun PaymentButton(drawableRes: Int) {
    val context = LocalContext.current
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { context.startActivity(Intent(context, DonacionRealizadaActivity::class.java))}
    )
}
