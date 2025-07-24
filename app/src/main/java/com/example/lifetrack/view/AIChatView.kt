package com.example.lifetrack.view

interface AIChatView {
    fun showLoading()
    fun hideLoading()
    fun displayAIResponse(response: String)
    fun showError(message: String)

}