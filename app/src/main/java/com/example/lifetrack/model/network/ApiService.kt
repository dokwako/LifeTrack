package com.example.lifetrack.model.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

class ApiService(private val client: HttpClient) {
    suspend fun authUser(userId: String): HttpResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.lifetrack.app"
                path("chats", userId)
            }
        }
    }

}