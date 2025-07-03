package com.example.lifetrack.view

import com.example.lifetrack.data.model.User

interface UserView{
    fun showUserData(user: User)
    fun updateUserUI(user: User)
    fun showMessage(message: String)
    fun showError(message: String)
    fun onLogout()
}