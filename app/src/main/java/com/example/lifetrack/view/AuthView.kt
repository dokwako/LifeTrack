package com.example.lifetrack.view

interface AuthView {
    fun showLoading(isLoading: Boolean, message: String? = null)
    fun onAuthSuccess()
    fun showError(message: String)
}
