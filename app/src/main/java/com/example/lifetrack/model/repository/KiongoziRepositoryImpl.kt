package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Kiongozi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import java.util.UUID

class KiongoziRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : KiongoziRepository {
//    override suspend fun getCurrentKiongozi(): Kiongozi? {
//        val adminId = auth.currentUser?.uid ?: return null
//        return getKiongoziById(adminId)
//    }
//
//    override suspend fun getKiongoziById(kiongoziId: String): Kiongozi? {
//        return try {
//            val snapshot = firestore.collection("Kiongos").document(kiongoziId).get().await()
//            snapshot.toObject(Kiongozi::class.java)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
    override suspend fun getViongozi(): List<Kiongozi> {
        val admins = mutableListOf<Kiongozi>()
        firestore.collection("Kiongos").get().await().forEach { document ->
            val kiongozi = document.toObject(Kiongozi::class.java)
            admins.add(kiongozi)
        }
        return admins
    }
    override suspend fun addKiongozi(admin: Kiongozi): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(admin.emailAddress, admin.passwordHash).await()
            val kiongoziId = result.user?.uid
            if (admin.uuid.isEmpty()) {
                admin.uuid = kiongoziId ?: UUID.randomUUID().toString()
            }
            admin.passwordHash = "null"
            firestore.collection("Kiongos").document(admin.uuid).set(admin).await()
//                AuthResult.SuccessWithData(fireRes)
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to add admin")
        }
    }
    override suspend fun updateKiongozi(admin: Kiongozi): AuthResult {
        return try {
            firestore.collection("Kiongos").document(admin.uuid).set(admin).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to update admin")
        }
    }
    override suspend fun deleteKiongozi(admin: Kiongozi): AuthResult {
        return try {
            firestore.collection("Kiongos").document(admin.uuid).delete().await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to delete admin")
        }
    }
    override suspend fun observeKiongozi(
        kiongoziId: String,
        onChange: (Kiongozi?) -> Unit
    ): ListenerRegistration {
        return firestore.collection("Kiongos")
            .document(kiongoziId)
            .addSnapshotListener { snapshot, _ ->
                val kiongozi = snapshot?.toObject(Kiongozi::class.java)
                onChange(kiongozi)
            }
    }
    override suspend fun logout() {
        auth.signOut()
    }

}