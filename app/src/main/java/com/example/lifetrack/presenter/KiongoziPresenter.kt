package com.example.lifetrack.presenter

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.Kiongozi
import com.example.lifetrack.model.repository.KiongoziRepository
import com.example.lifetrack.model.repository.KiongoziRepositoryImpl
import com.example.lifetrack.view.KiongoziView
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KiongoziPresenter(
    private val view: KiongoziView,
    private val kiongoziRepository: KiongoziRepository = KiongoziRepositoryImpl()
) {
    private val kiongoziListener: ListenerRegistration? = null

    suspend fun getCurrentKiongozi() {
        CoroutineScope(Dispatchers.Main)
        kiongoziRepository.getCurrentKiongozi()
    }

    fun startKiongoziObserver(kiongoziId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val kiongozi = kiongoziRepository.getKiongoziById(kiongoziId)
            if (kiongozi != null) {
                view.showKiongoziData(kiongozi)
            } else {
                view.showError("Kiongozi not found (observer)")
            }
        }
    }

    fun stopKiongoziObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            kiongoziListener?.remove()
        }
    }

    fun getKiongoziById(kiongoziId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val kiongozi = kiongoziRepository.getKiongoziById(kiongoziId)
            if (kiongozi != null) {
                view.showKiongoziData(kiongozi)
            } else {
                view.showError("Kiongozi not found (by Id)")
            }
        }
    }

    fun getViongozi(onResult: (List<Kiongozi>) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val viongozi = kiongoziRepository.getViongozi()
            onResult(viongozi)
        }
    }

    fun addKiongozi(kiongozi: Kiongozi, onAdd: (Kiongozi) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = kiongoziRepository.addKiongozi(kiongozi)
            when (result) {
                is AuthResult.Success -> view.showMessage("Kiongozi added successfully")
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> view.showMessage("Adding Kiongozi...")
                is AuthResult.SuccessWithData<*> -> onAdd(result.data as Kiongozi)
            }
        }
    }

    fun updateKiongozi(kiongozi: Kiongozi, onUpdate: (Kiongozi) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = kiongoziRepository.updateKiongozi(kiongozi)
            when (result) {
                is AuthResult.Success -> view.showMessage("Kiongozi updated successfully")
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> view.showMessage("Updating Kiongozi...")
                is AuthResult.SuccessWithData<*> -> onUpdate(result.data as Kiongozi)
            }
        }
    }

    fun deleteKiongozi(kiongozi: Kiongozi, onDelete: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = kiongoziRepository.deleteKiongozi(kiongozi)
            when (result) {
                is AuthResult.Success -> view.showMessage("Kiongozi deleted successfully")
                is AuthResult.Failure -> view.showError(result.message)
                AuthResult.Loading -> view.showMessage("Deleting Kiongozi...")
                is AuthResult.SuccessWithData<*> -> onDelete()
            }
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            kiongoziRepository.logout()
        }
    }
}


