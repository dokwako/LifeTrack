package com.example.lifetrack.model.repository

//import android.util.Log
import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await


class UserRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val authRepository: AuthRepository = AuthRepositoryImpl(auth, firestore)
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
            AuthResult.Failure(e.message ?: "Failed to get user by ID")
            null
        }
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

    override suspend fun getPatients(): List<User> {
        val patients = mutableListOf<User>()
        val snapshot = firestore.collection("Patients").get().await()
        snapshot.forEach { document ->
            val patient = document.toObject(User::class.java)
            patients.add(patient)
        }
        return patients
    }

    override suspend fun addPatient(patient: User): AuthResult {
        return try {
            val result = authRepository.signUp(
                email = patient.emailAddress,
                password = patient.password,
                phoneNumber = patient.phoneNumber,
                displayName = patient.fullName
            )
            AuthResult.SuccessWithData(result)
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to save patient data")
        }
    }

    override suspend fun updatePatient(patient: User): AuthResult {
        return try {
            firestore.collection("Patients")
                .document(patient.uuid)
                .set(patient)
                .await()
            AuthResult.SuccessWithData(patient)
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to update patient")
        }
    }

    override suspend fun deletePatient(patient: User): AuthResult {
        return try {
            firestore.collection("Patients")
                .document(patient.uuid)
                .delete()
                .await()
            if (auth.currentUser != null && auth.currentUser?.uid == patient.uuid) {
                auth.currentUser?.delete()?.await()
            }
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to delete patient")
        }
    }
    override suspend fun logout() {
        auth.signOut()
    }
}