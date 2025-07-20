package com.example.lifetrack.view

import com.example.lifetrack.model.data.AuthResult

interface AuthView {
    fun showLoading(isLoading: Boolean, message: String? = null)
    fun onAuthSuccess()
    fun onAuthSuccessWithData(data: String)
    fun showError(message: String)
}
