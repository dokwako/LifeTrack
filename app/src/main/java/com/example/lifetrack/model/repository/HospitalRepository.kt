package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.Hospital
import com.google.firebase.firestore.ListenerRegistration

interface HospitalRepository {
    suspend fun getHospitals(): List<Hospital>
    suspend fun getHospitalById(hospitalId: String): Hospital?
    suspend fun addHospital()
    suspend fun updateHospital(hospitalId: String)
    suspend fun deleteHospital(hospitalId: String)
    fun observeHospital(hospitalId: String, onChange: (Hospital?) -> Unit): ListenerRegistration

}