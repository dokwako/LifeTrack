package com.example.lifetrack.presenter

import android.util.Log
import com.example.lifetrack.model.network.ApiService
import com.example.lifetrack.view.AIChatView
import kotlinx.coroutines.*

class ChatPresenter(
    private val apiService: ApiService
) {

    private var view: AIChatView? = null
    private val presenterJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + presenterJob)

    fun attachView(view: AIChatView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
        presenterJob.cancel()
    }

    fun sendMessageToAI(prompt: String) {
        scope.launch {
            view?.showLoading()
            try {
                val aiResponse = withContext(Dispatchers.IO) {
                    apiService.getAIResponse(prompt)
                }
                view?.displayAIResponse(aiResponse)
                Log.d("ChatPresenter", "AI Response: $aiResponse")
            } catch (e: Exception) {
                view?.showError(e.localizedMessage ?: "Unknown Error")
                Log.e("ChatPresenter", "Error fetching AI response: ${e.localizedMessage}", e)
            } finally {
                view?.hideLoading()
            }
        }
    }
}
