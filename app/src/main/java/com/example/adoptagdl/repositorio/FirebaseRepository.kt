package com.example.adoptagdl.repositorio

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

object FirebaseRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun registrarAdoptante(
        nombre: String,
        telefono: String,
        colonia: String,
        tipoMascota: String,
        correo: String,
        contrasena: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                    val data = hashMapOf(
                        "nombre" to nombre,
                        "telefono" to telefono,
                        "colonia" to colonia,
                        "tipoMascota" to tipoMascota,
                        "correo" to correo
                    )

                    db.collection("adoptantes").document(uid).set(data)
                        .addOnSuccessListener { onResult(true, null) }
                        .addOnFailureListener { e -> onResult(false, e.message) }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun registrarRefugio(
        nombreRefugio: String,
        direccion: String,
        contacto: String,
        correo: String,
        contrasena: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                    val refugioData = hashMapOf(
                        "nombreRefugio" to nombreRefugio,
                        "direccion" to direccion,
                        "contacto" to contacto,
                        "correo" to correo
                    )

                    db.collection("refugios").document(uid).set(refugioData)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message)
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
}

