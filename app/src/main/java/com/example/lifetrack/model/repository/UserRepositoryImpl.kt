package com.example.lifetrack.model.repository

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

    override suspend fun getPatients(): List<User> {
        return try {
            val patients = mutableListOf<User>()
            val snapshot = firestore.collection("Patients").get().await()
            snapshot.forEach { document ->
                val patient = document.toObject(User::class.java)
                patients.add(patient)
            }
            patients
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to get patients")
            emptyList()
        }
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
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Failed to delete patient")
        }
    }
}