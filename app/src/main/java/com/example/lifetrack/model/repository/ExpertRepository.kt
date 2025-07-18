package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Practitioner
import com.google.firebase.firestore.ListenerRegistration

interface ExpertRepository {
    suspend fun getCurrentPractitioner(): Practitioner?
    suspend fun getPractitionerById(practitionerId: String): Practitioner?
    suspend fun sendPasswordReset(email: String): AuthResult
    suspend fun getPractitioners(): List<Practitioner>
    suspend fun addPractitioner(practitioner: Practitioner): AuthResult
    suspend fun updatePractitioner(practitioner: Practitioner): AuthResult
    suspend fun deletePractitioner(practitioner: Practitioner): AuthResult
    suspend fun logout()
    fun observePractitioner(userId: String, onChange: (Practitioner?) -> Unit): ListenerRegistration


}