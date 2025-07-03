package com.example.lifetrack.data.model

data class Practitioner(
    val accessLevel: Int = 0,
    val hospitalId: String,
    val lifetrackId: String,
    val fullName: String = "",
    val phoneNumber: String = "",
    val emailAddress: String = "",
    val role: String = "practitioner",
    val profileImageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)