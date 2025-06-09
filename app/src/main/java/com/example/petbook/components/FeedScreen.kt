
package com.example.petbook.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.petbook.R
import com.example.petbook.activities.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun PostCard(onMenuClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var postText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .background(Color(0xFFBFD9EE))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.lineas),
                contentDescription = "Menú",
                modifier = Modifier.size(35.dp).clickable { onMenuClick() }
            )
            Image(
                painter = painterResource(id = R.drawable.logo_petbook),
                contentDescription = "Logo Petbook",
                modifier = Modifier.size(90.dp)
            )
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notificaciones",
                modifier = Modifier.size(35.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Realizar un post:", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_add_foto),
                        contentDescription = "Agregar imagen",
                        modifier = Modifier.size(36.dp).clickable {
                            imagePickerLauncher.launch("image/*")
                        }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    TextField(
                        value = postText,
                        onValueChange = { postText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = {},
                        shape = RoundedCornerShape(0.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        )
                    )
                }

                imageUri?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_publicar),
                        contentDescription = "Publicar",
                        modifier = Modifier
                            .size(width = 80.dp, height = 36.dp)
                            .clickable {
                                Toast.makeText(context, "Publicado: $postText", Toast.LENGTH_SHORT).show()
                                postText = ""
                                imageUri = null
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PostItem(
            username = "@kuro",
            time = "Hace 20 minutos",
            imageRes = R.drawable.kuro,
            comments = listOf("@milacherry: cute ;)", "@kero: Que bonita!")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "@catsanddogsofficial",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Veterinarios emiten aviso importante para dueños de perros y gatos...\n\n" +
                            "Expertos han dado recomendaciones clave para mejorar la convivencia entre perros y gatos en un mismo hogar.",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_bubble_outline),
                        contentDescription = "Comentarios",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Likes")
                }
            }
        }
    }
}



@Composable
fun PostItem(username: String, time: String, imageRes: Int, comments: List<String>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(username, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Text(time, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Foto de la mascota",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_bubble_outline),
                    contentDescription = "Comentarios",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Likes")
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFF87CEEB), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            comments.forEach {
                Text(it, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PostCardWithDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .fillMaxHeight()
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 100.dp)
                        .height(400.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF495C8E),
                            shape = RoundedCornerShape(
                                topEnd = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "MENÚ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .padding(bottom = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        MenuItem("Mi perfil", R.drawable.ic_dog_paw) {
                            val auth = FirebaseAuth.getInstance()
                            val db = FirebaseFirestore.getInstance()
                            val user = auth.currentUser

                            user?.let {
                                db.collection("mascotas")
                                    .whereEqualTo("correo", user.email)
                                    .get()
                                    .addOnSuccessListener { querySnapshot ->
                                        if (!querySnapshot.isEmpty) {
                                            context.startActivity(Intent(context, ProfileActivity::class.java))
                                        } else {
                                            db.collection("clinicas").document(user.uid).get()
                                                .addOnSuccessListener { clinicaDoc ->
                                                    if (clinicaDoc.exists()) {
                                                        context.startActivity(Intent(context, ClinicaPerfilActivity::class.java))
                                                    } else {
                                                        db.collection("albergues").document(user.uid).get()
                                                            .addOnSuccessListener { albergueDoc ->
                                                                if (albergueDoc.exists()) {
                                                                    context.startActivity(Intent(context, AlberguePerfilActivity::class.java))
                                                                } else {
                                                                    Toast.makeText(context, "Tipo de perfil no encontrado", Toast.LENGTH_SHORT).show()
                                                                }
                                                            }
                                                    }
                                                }
                                        }
                                    }
                            }
                        }



                        MenuItem("Veterinarios en mi zona", R.drawable.ic_estetoscopio) {
                            context.startActivity(Intent(context, VeterinariosActivity::class.java))
                        }

                        MenuItem("Parques en mi zona", R.drawable.ic_tree) {
                            context.startActivity(Intent(context, ParquesActivity::class.java))
                        }

                        MenuItem("Mascotas en mi zona", R.drawable.ic_cat) {
                            context.startActivity(Intent(context, MascotasActivity::class.java))
                        }

                        MenuItem("Noticias y Adopción", R.drawable.ic_admiracion) {
                            context.startActivity(Intent(context, NoticiasAdopcionesActivity::class.java))
                        }
                    }
                }
            }
        }
    ) {
        PostCard(onMenuClick = { scope.launch { drawerState.open() } })
    }
}

@Composable
fun MenuItem(text: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = Color.Black, fontWeight = FontWeight.Medium)
    }
}

@Composable
@Preview
fun PostCardPreview() {
    PostCard(onMenuClick = {})
}

@Composable
@Preview
fun PostCardWithDrawerPreview() {
    PostCardWithDrawer()
}
