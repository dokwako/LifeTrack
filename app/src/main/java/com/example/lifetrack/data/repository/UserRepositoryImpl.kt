package com.example.lifetrack.data.repository

import com.example.lifetrack.data.model.AuthResult
import com.example.lifetrack.data.model.Practitioner
import com.example.lifetrack.data.model.User
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import android.util.Log


class UserRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository {

    override suspend fun getCurrentUser(): User? {
        val userId = auth.currentUser?.uid ?: return null
        return getUserById(userId)
    }

    override suspend fun getUserById(userId: String): User? {
        return try {
            val snapshot = firestore.collection("Patients").document(userId).get().await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateUser(userId: String, user: User): AuthResult {
        return try {
            val updatedUser = user.copy(updatedAt = System.currentTimeMillis())
            firestore.collection("Patients").document(userId).set(updatedUser).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to update user")
        }
    }

    override suspend fun deleteUser(userId: String): AuthResult {
        return try {
            firestore.collection("Patients").document(userId).delete().await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to delete user")
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun sendPasswordReset(email: String): AuthResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to send reset email")
        }
    }

    override fun observeUser(userId: String, onChange: (User?) -> Unit): ListenerRegistration {
        return firestore.collection("Patients")
            .document(userId)
            .addSnapshotListener { snapshot, _ ->
                val user = snapshot?.toObject(User::class.java)
                onChange(user)
            }
    }
    override suspend fun getPractitioners(): List<Practitioner> {
        try {
            val snapshot = firestore.collection("Practitioners").get().await()
            return snapshot.documents.mapNotNull { it.toObject(Practitioner::class.java) }
        } catch (e: Exception) {
            return emptyList()
        }
    }
    
    override suspend fun addPractitioner(practitioner: Practitioner): AuthResult {
        return try {
            firestore.collection("Practitioners")
                .document(auth.uid.toString())
                .set(practitioner)
                .await()
            AuthResult.SuccessWithData(practitioner)
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to add practitioner")
        }
    }

    override suspend fun updatePractitioner(practitioner: Practitioner): AuthResult {
        return try {
            firestore.collection("Practitioners")
                .document(auth.uid.toString())
                .set(practitioner)
                .await()
            AuthResult.SuccessWithData(practitioner)
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to update practitioner")
        }
    }

    override suspend fun deletePractitioner(practitioner: Practitioner): AuthResult {
        return try {
            firestore.collection("Practitioners")
                .document(practitioner.lifetrackId)
                .delete()
                .await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to delete practitioner")
        }
    }
}