package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Practitioner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class ExpertRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()): ExpertRepository {

    override suspend fun getCurrentPractitioner(): Practitioner? {
        val practitionerId  = auth.currentUser?.uid ?: return null
        return getPractitionerById(practitionerId)
    }

    override suspend fun getPractitionerById(practitionerId: String): Practitioner? {
        return try {
            val snapshot = firestore.collection("Practitioners").document(practitionerId).get().await()
            snapshot.toObject(Practitioner::class.java)
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to get practitioner by ID")
            null
        }
    }

    override suspend fun sendPasswordReset(email: String): AuthResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to send reset email ${e.toString()}")
        }
    }

    override fun observePractitioner(userId: String, onChange: (Practitioner?) -> Unit): ListenerRegistration {
        return firestore.collection("Practitioners")
            .document(userId)
            .addSnapshotListener { snapshot, _ ->
                val practitioner = snapshot?.toObject(Practitioner::class.java)
                onChange(practitioner)
            }
    }
    override suspend fun getPractitioners(): List<Practitioner> {
        try {
            val snapshot = firestore.collection("Practitioners").get().await()
            return snapshot.documents.mapNotNull { it.toObject(Practitioner::class.java) }
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to get practitioners")
            return emptyList()

        }
    }

    override suspend fun addPractitioner(practitioner: Practitioner): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(practitioner.emailAddress, practitioner.password).result
            if (result == null || result.user == null) {
                return AuthResult.Failure("FirebaseAuth error on creating user")
            }
            practitioner.uuid = result.user?.uid ?: java.util.UUID.randomUUID().toString()
            firestore.collection("Practitioners")
                .document(practitioner.uuid)
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
                .document(practitioner.uuid)
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
                .document(practitioner.uuid)
                .delete()
                .await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to delete practitioner")
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }


}