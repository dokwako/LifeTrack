package com.example.lifetrack.data.model

data class User(
    val lifetrackId: String?,
    val emailAddress: String,
    val phoneNumber: String,
    val fullName: String = "",
    val profileImageUrl: String = "",
    val role: String = "patient",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)











