package com.example.lifetrack.model.network

import kotlinx.coroutines.*
import io.ktor.client.HttpClient


class SyncEngine(private val apiService: HttpClient) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun startSync() {
        scope.launch {
//            syncUserProfile()
        }
    }

    companion object {
        fun createDefault(): SyncEngine {
            val client = KtorClientFactory().create()
            return SyncEngine(client)
        }
    }
    fun stopSync() {
        scope.cancel()
    }
}