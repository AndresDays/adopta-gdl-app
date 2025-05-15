package com.example.adoptagdl.screens

import android.content.Intent
import android.provider.ContactsContract.Profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.adoptagdl.activities.AboutUsActivity
import com.example.adoptagdl.activities.FeedActivity
import com.example.adoptagdl.activities.InboxActivity
import com.example.adoptagdl.activities.ProfileActivity
import com.example.adoptagdl.activities.RefugiosActivity
import com.example.adoptagdl.activities.ReportsActivity

@Composable
fun FeedScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_rosa),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )



            Spacer(modifier = Modifier.height(78.dp))

            MenuImageButton(R.drawable.btn_inicio_conocenos) {
                context.startActivity(Intent(context, AboutUsActivity::class.java))
            }

            Spacer(modifier = Modifier.height(20.dp))

            MenuImageButton(R.drawable.btn_inicio_mascotas_disponibles) {
                // TODO: navegación a mascotas
            }

            Spacer(modifier = Modifier.height(20.dp))

            MenuImageButton(R.drawable.btn_inicio_explorar_albergues) {
                context.startActivity(Intent(context, RefugiosActivity::class.java))
            }

            Spacer(modifier = Modifier.height(20.dp))

            MenuImageButton(R.drawable.btn_inicio_reporte_maltrato) {
                context.startActivity(Intent(context, ReportsActivity::class.java))
            }
        }

        BottomNavigationBar(current = "home", modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun MenuImageButton(imageRes: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .width(240.dp)
            .height(64.dp)
            .clickable { onClick() }
    )
}





@Composable
fun BottomNavigationBar(current: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomIcon(
                iconRes = if (current == "home") R.drawable.icon_home_rosa else R.drawable.icon_home
            ) {
                context.startActivity(Intent(context, FeedActivity::class.java))
            }
            BottomIcon(
                iconRes = if (current == "search") R.drawable.icon_search_rosa else R.drawable.icon_search
            ) {
                // Abrir búsqueda
            }
            BottomIcon(
                iconRes = if (current == "like") R.drawable.icon_like_rosa else R.drawable.icon_like
            ) {
                // Abrir favoritos
            }
            BottomIcon(
                iconRes = if (current == "inbox") R.drawable.icon_msj_rosa else R.drawable.icon_msj
            ) {
                context.startActivity(Intent(context, InboxActivity::class.java))
            }
            BottomIcon(
                iconRes = if (current == "profile") R.drawable.icon_usu_rosa else R.drawable.icon_usu
            ) {
                context.startActivity(Intent(context, ProfileActivity::class.java))
            }
        }
    }
}

@Composable
fun BottomIcon(iconRes: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(36.dp)
            .clickable(onClick = onClick)
    )
}
