package com.example.lifetrack.model.data

import kotlinx.serialization.Serializable
//import io.ktor.serialization.kotlinx.

@Serializable
data class ChatMessage(
    val text: String,
    val isUser: Boolean
)