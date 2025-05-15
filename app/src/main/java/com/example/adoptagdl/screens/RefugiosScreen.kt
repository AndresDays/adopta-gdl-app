package com.example.adoptagdl.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adoptagdl.R
import com.example.adoptagdl.activities.RefugioDetalleActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

@Composable
fun RefugiosScreen() {
    val db = FirebaseFirestore.getInstance()
    var documentos by remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }

    LaunchedEffect(Unit) {
        val snapshot = db.collection("refugios").get().await()
        documentos = snapshot.documents
    }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_verde),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.albergues_titulo),
                contentDescription = null,
                modifier = Modifier
                    .width(240.dp)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(documentos) { document ->
                    val refugio = document.data ?: return@items
                    RefugioCard(refugio = refugio) {
                        val intent = Intent(context, RefugioDetalleActivity::class.java).apply {
                            putExtra("refugioId", document.id)
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }

        BottomNavigationBar(
            current = "search",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun RefugioCard(refugio: Map<String, Any>, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rectangulo_azul),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangulo_blanco),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    painter = painterResource(id = R.drawable.refugio1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.btn_ver),
                contentDescription = "Ver más",
                modifier = Modifier
                    .height(46.dp)
                    .clickable { onClick() }
            )
        }
    }
}
