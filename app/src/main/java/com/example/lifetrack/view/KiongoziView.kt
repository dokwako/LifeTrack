package com.example.lifetrack.view

import com.example.lifetrack.model.data.Kiongozi

interface KiongoziView {
//    fun showKiongoziData(kiongozi: Kiongozi?)
    fun updateKiongoziUI(kiongozi: Kiongozi)
    fun showMessage(message: String)
    fun showError(message: String)
    fun onLogout()

}