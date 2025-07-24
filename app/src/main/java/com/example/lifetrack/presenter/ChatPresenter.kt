package com.example.lifetrack.presenter

import com.example.lifetrack.model.network.ApiService
import com.example.lifetrack.view.AIChatView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

class ChatPresenter(
    private val view: AIChatView,
    private val apiService: ApiService
) {

    fun sendMessageToAI(prompt: String) {
        CoroutineScope(Dispatchers.Main).launch {
            view.showLoading()
            try {
                val aiResponse = apiService.getAIResponse(prompt)
                view.displayAIResponse(aiResponse)
                Log.d("ChatPresenter", "AI Response: $aiResponse")
            } catch (e: Exception) {
                view.showError(e.localizedMessage ?: "Unknown Error")
                Log.d("ChatPresenter", "Error fetching AI response: ${e.localizedMessage}")
            } finally {
                view.hideLoading()
            }
        }
    }

}


