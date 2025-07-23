package com.example.lifetrack.presenter

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.User
import com.example.lifetrack.view.UserView
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.lifetrack.model.repository.UserRepositoryImpl
import kotlinx.coroutines.SupervisorJob

class UserPresenter(
    private val view: UserView,
    private val userRepository: UserRepositoryImpl
) {
    private var userListener: ListenerRegistration? = null
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun loadCurrentUser() {
        CoroutineScope(Dispatchers.Main).launch {
            val user = userRepository.getCurrentUser()
            if (user != null) {
                view.showUserData(user)
            } else {
                view.showError("User not found")
            }
        }
    }
    fun startUserObserver(userId: String) {
        userListener = userRepository.observeUser(userId) { user: User? ->
            user?.let {
                view.updateUserUI(it)
            }
        }
    }
    fun stopUserObserver() {
        userListener?.remove()
    }

    fun sendPasswordReset(email: String) {
        coroutineScope.launch {
            when (val result = userRepository.sendPasswordReset(email)) {
                is AuthResult.Success -> view.showMessage("Reset email sent")
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> view.showMessage("Sending password reset email...")
                is AuthResult.SuccessWithData<*> -> {}
            }
        }
    }

    fun getPatients(onResult: (List<User>) -> Unit) {
        coroutineScope.launch {
            val patients = userRepository.getPatients()
            onResult(patients)
        }
    }

    fun addPatient(patient: User, onResult: (User) -> Unit) {
        coroutineScope.launch {
            val result = userRepository.addPatient(patient)
            when (result) {
                is AuthResult.SuccessWithData<*> -> onResult(result.data as User)
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> {view.showMessage("Adding patient...")}
                is AuthResult.Success -> {view.showMessage("Success")}
            }
        }
    }

    fun updatePatient(patient: User, onResult: (User) -> Unit) {
        coroutineScope.launch {
            val result = userRepository.updatePatient(patient)
            when (result) {
                is AuthResult.SuccessWithData<*> -> onResult(result.data as User)
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> {}
                is AuthResult.Success -> {}
            }
        }
    }

    fun deletePatient(patient: User, onResult: () -> Unit) {
        coroutineScope.launch {
            val result = userRepository.deletePatient(patient)
            when (result) {
                is AuthResult.Success -> onResult()
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> {}
                is AuthResult.SuccessWithData<*> -> {}
            }
        }
    }

    fun logout() {
        coroutineScope.launch {
            userRepository.logout()
            view.onLogout()
        }
    }
}