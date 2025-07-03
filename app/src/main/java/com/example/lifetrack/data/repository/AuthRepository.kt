package com.example.lifetrack.data.repository

import com.example.lifetrack.data.model.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String, phoneNumber: String, displayName: String): AuthResult
}
