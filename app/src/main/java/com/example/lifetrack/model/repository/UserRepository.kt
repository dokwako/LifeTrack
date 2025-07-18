package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.User
import com.example.lifetrack.model.data.AuthResult
import com.google.firebase.firestore.ListenerRegistration

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(userId: String, user: User): AuthResult
    suspend fun deleteUser(userId: String): AuthResult
    suspend fun sendPasswordReset(email: String): AuthResult
    suspend fun logout()
    fun observeUser(userId: String, onChange: (User?) -> Unit): ListenerRegistration

    suspend fun getPatients(): List<User>
    suspend fun addPatient(patient: User): AuthResult
    suspend fun updatePatient(patient: User): AuthResult
    suspend fun deletePatient(patient: User): AuthResult
}