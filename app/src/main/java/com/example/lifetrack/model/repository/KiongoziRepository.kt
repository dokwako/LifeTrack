package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Kiongozi
import com.google.firebase.firestore.ListenerRegistration

interface KiongoziRepository {
    suspend fun getCurrentKiongozi(): Kiongozi?
    suspend fun getKiongoziById(kiongoziId: String): Kiongozi?
    suspend fun getViongozi(): List<Kiongozi>
    suspend fun addKiongozi(admin: Kiongozi): AuthResult
    suspend fun updateKiongozi(admin: Kiongozi): AuthResult
    suspend fun deleteKiongozi(admin: Kiongozi): AuthResult
    suspend fun observeKiongozi(kiongoziId: String, onChange: (Kiongozi?) -> Unit): ListenerRegistration
    suspend fun logout()
}