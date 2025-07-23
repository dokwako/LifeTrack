package com.example.lifetrack.presenter

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Practitioner
import com.example.lifetrack.view.ExpertView
import com.example.lifetrack.model.repository.ExpertRepository
import com.example.lifetrack.model.repository.ExpertRepositoryImpl
//import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpertPresenter(
    private val view: ExpertView? = null,
    private val practitionerRepository: ExpertRepository = ExpertRepositoryImpl()
){
//    private var practitionerListener: ListenerRegistration? = null
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun getCurrentPractitioner() {
        coroutineScope.launch {
            val practitioner = practitionerRepository.getCurrentPractitioner()
            withContext(Dispatchers.Main) {
                if (practitioner != null) {
                    view?.updateExpertUI(practitioner)
                } else {
                    view?.showError("Practitioner not found")
                }
            }
        }
    }
    
    fun getPractitionerById(practitionerId: String) {
        coroutineScope.launch {
            val practitioner = practitionerRepository.getPractitionerById(practitionerId)
            withContext(Dispatchers.Main) {
                if (practitioner != null) {
                    view?.showExpertData(practitioner)
                } else {
                    view?.showError("Practitioner not found (by Id)")
                }
            }
        }
    }
    fun sendPasswordReset(email: String) {
        coroutineScope.launch {
            val result = practitionerRepository.sendPasswordReset(email)
            withContext(Dispatchers.Main) {
                when (result) {
                    is AuthResult.Success -> {
                        view?.showMessage("Password reset email sent")
                    }
                    is AuthResult.Failure -> {
                        view?.showError(result.message)
                    }
                    is AuthResult.Loading -> {
                        view?.showLoading(true, "Sending password reset email...")
                    }
                    is AuthResult.SuccessWithData<*> -> view?.showExpertData(result.data.toString())
                }
            }
        }
    }

    fun getPractitioners(onResult: (List<Practitioner>) -> Unit) {
        coroutineScope.launch {
            val practitioners = practitionerRepository.getPractitioners()
            onResult(practitioners)
        }
    }

    fun addPractitioner(practitioner: Practitioner, onResult: (Practitioner) -> Unit) {
        coroutineScope.launch {
            val result = practitionerRepository.addPractitioner(practitioner)
            withContext(Dispatchers.Main) {
                when (result) {
                    is AuthResult.SuccessWithData<*> -> onResult(result.data as Practitioner)
                    is AuthResult.Failure -> view?.showError(result.message)
                    AuthResult.Loading -> {
                        view?.showLoading(true, "Adding practitioner...")
                    }
                    is AuthResult.Success -> {
                        view?.showMessage("Practitioner added successfully")
                    }
                }
            }
        }
    }

    fun updatePractitioner(practitioner: Practitioner, onResult: (Practitioner) -> Unit) {
        coroutineScope.launch {
            val result = practitionerRepository.updatePractitioner(practitioner)
            withContext(Dispatchers.Main) {
                when (result) {
                    is AuthResult.SuccessWithData<*> -> onResult(result.data as Practitioner)
                    is AuthResult.Failure -> view?.showError(result.message)
                    AuthResult.Loading -> {
                        view?.showLoading(true, "Updating practitioner...")
                    }
                    is AuthResult.Success -> {
                        view?.showMessage("Practitioner updated successfully")
                    }
                }
            }
        }
    }
    fun deletePractitioner(practitioner: Practitioner, onResult: () -> Unit) {
        coroutineScope.launch {
            val result = practitionerRepository.deletePractitioner(practitioner)
            withContext(Dispatchers.Main) {
                when (result) {
                    is AuthResult.Success -> onResult()
                    is AuthResult.Failure -> view?.showError(result.message)
                    AuthResult.Loading -> {
                        view?.showLoading(true, "Deleting practitioner...")
                    }
                    is AuthResult.SuccessWithData<*> -> {
                        view?.showMessage("Practitioner deleted successfully")
                    }
                }
            }
        }
    }
    fun logout() {
        coroutineScope.launch {
            val result = practitionerRepository.logout()
            when (result) {
                AuthResult.Loading -> {
                    withContext(Dispatchers.Main) {
                        view?.showLoading(true, "Logging out...")
                    }
                }
            }
        }
    }
}
