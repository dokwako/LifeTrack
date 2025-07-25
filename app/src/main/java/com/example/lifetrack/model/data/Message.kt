package com.example.lifetrack.model.data

data class Message(
    val id: String,
    val text: String,
    val isFromPatient: Boolean,
    val timestamp: String // Simplified for mock
)
