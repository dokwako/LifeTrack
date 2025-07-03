package com.example.lifetrack.data.repository

import com.example.lifetrack.data.model.User
import com.example.lifetrack.data.model.AuthResult
import com.example.lifetrack.data.model.Practitioner
import com.google.firebase.firestore.ListenerRegistration

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(userId: String, user: User): AuthResult
    suspend fun deleteUser(userId: String): AuthResult
    suspend fun sendPasswordReset(email: String): AuthResult
    suspend fun logout()
    fun observeUser(userId: String, onChange: (User?) -> Unit): ListenerRegistration

    // Practitioner CRUD
    suspend fun getPractitioners(): List<Practitioner>
    suspend fun addPractitioner(practitioner: Practitioner): AuthResult
    suspend fun updatePractitioner(practitioner: Practitioner): AuthResult
    suspend fun deletePractitioner(practitioner: Practitioner): AuthResult
}