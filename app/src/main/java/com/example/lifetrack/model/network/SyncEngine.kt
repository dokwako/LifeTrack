package com.example.lifetrack.model.network

import kotlinx.coroutines.*
import com.example.lifetrack.model.network.ApiService


class SyncEngine(private val apiService: ApiService) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun startSync() {
        scope.launch {
            syncUserProfile()
        }
    }

    private suspend fun syncUserProfile() {
        val userProfile = apiService.authUser("user_id")?: null
        println("Synced user profile: $userProfile")
    }

    fun stopSync() {
        scope.cancel()
    }
}