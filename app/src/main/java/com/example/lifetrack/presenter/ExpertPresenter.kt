package com.example.lifetrack.presenter

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Practitioner
import com.example.lifetrack.view.ExpertView
import com.example.lifetrack.model.repository.ExpertRepository
import com.example.lifetrack.model.repository.ExpertRepositoryImpl
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpertPresenter(
    private val view: ExpertView? = null,
    private val practitionerRepository: ExpertRepository = ExpertRepositoryImpl()
){
    private var practitionerListener: ListenerRegistration? = null

    fun startPractitionerObserver(practitionerId: String) {
        view?.showLoading(true)
        practitionerListener = practitionerRepository.observePractitioner(practitionerId) {
            view?.showLoading(false)
            it?.let {
                view?.updateExpertUI(it)
            }
        }
    }
    fun detachPractitionerObserver() {
        practitionerListener?.remove()
    }

    fun getCurrentPractitioner() {
        CoroutineScope(Dispatchers.Main).launch {
            val practitioner = practitionerRepository.getCurrentPractitioner()
            if (practitioner != null) {
                view?.updateExpertUI(practitioner)
            } else {
                view?.showError("Practitioner not found")
            }
        }
    }
    
    fun getPractitionerById(practitionerId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val practitioner = practitionerRepository.getPractitionerById(practitionerId)
            if (practitioner != null) {
                view?.showExpertData(practitioner)
            } else {
                view?.showError("Practitioner not found (by Id)")
            }
        }
    }
    fun sendPasswordReset(email: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = practitionerRepository.sendPasswordReset(email)
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

    fun getPractitioners(onResult: (List<Practitioner>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val practitioners = practitionerRepository.getPractitioners()
            onResult(practitioners)
        }
    }

    fun addPractitioner(practitioner: Practitioner, onResult: (Practitioner) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = practitionerRepository.addPractitioner(practitioner)
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

    fun updatePractitioner(practitioner: Practitioner, onResult: (Practitioner) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = practitionerRepository.updatePractitioner(practitioner)
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
    fun deletePractitioner(practitioner: Practitioner, onResult: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = practitionerRepository.deletePractitioner(practitioner)
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
    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = practitionerRepository.logout()
            when (result) {
                AuthResult.Loading -> {
                    view?.showLoading(true, "Logging out...")
                }
            }
        }
    }
}
