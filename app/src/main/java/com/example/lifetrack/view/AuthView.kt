package com.example.lifetrack.view


interface AuthView {
    fun showLoading(isLoading: Boolean, message: String? = null)
    fun onAuthSuccess()
    fun onAuthSuccessWithData(data: String)
    fun showError(message: String)
}
