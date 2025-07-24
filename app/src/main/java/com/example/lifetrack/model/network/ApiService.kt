package com.example.lifetrack.model.network

import com.example.lifetrack.model.repository.AuthRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
//import kotlinx.serialization.descriptors.*
//import kotlinx.serialization.encoding.*
//import android.util.Log


@Serializable
data class VertexAIRequest(
    val instances: List<Map<String, String>>
)

@Serializable
data class VertexAIResponse(
    val predictions: List<Map<String, JsonElement>>
)

@Serializable
data class VertexAIErrorResponse(
    val error: ErrorDetails
)

@Serializable
data class ErrorDetails(
    val code: Int,
    val message: String
)

class ApiService(private val client: HttpClient, private val authRepository: AuthRepository) {

    suspend fun getAIResponse(prompt: String): String {
        val endpoint = "https://us-central1-aiplatform.googleapis.com/v1/projects/lifetrack-666/locations/us-central1/publishers/google/models/gemini-2.0-flash-001:predict"

        val accessToken = getAccessToken() // Fetch OAuth2 token

        val requestBody = VertexAIRequest(
            instances = listOf(mapOf("content" to prompt))
        )

        val response: HttpResponse = client.post(endpoint) {
            header("Authorization", "Bearer $accessToken") //
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }

        val responseText = response.bodyAsText()

        if (!response.status.isSuccess()) {
            return "HTTP Error ${response.status}: $responseText"
        }

        return try {
            val jsonResponse = Json.decodeFromString<VertexAIResponse>(responseText)
            jsonResponse.predictions.firstOrNull()?.get("content")?.toString() ?: "No response"
        } catch (e: SerializationException) {
            try {
                val errorResponse = Json.decodeFromString<VertexAIErrorResponse>(responseText)
                "API Error: ${errorResponse.error.message}"
            } catch (ex: SerializationException) {
                "Failed to parse response: ${ex.message}"
            }
        }
    }


    private suspend fun getAccessToken(): String {
        return authRepository.getTokenId().also { token ->
            if (token.isEmpty()) {
                throw IllegalStateException("Access token is empty. Please login first.")
            }
        }
    }
}
