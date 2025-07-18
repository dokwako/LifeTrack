package com.example.lifetrack.view

import com.example.lifetrack.model.data.Practitioner

interface ExpertView {
    fun showLoading(isLoading: Boolean, message: String? = null)
    fun showError(message: String)
    fun updateExpertUI(it: Practitioner)
    fun showExpertData(it: Any)
    fun showMessage(message: String)
}