package com.example.lifetrack.model.repository

import com.example.lifetrack.model.data.AuthResult
import com.example.lifetrack.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import android.util.Log


class AuthRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : AuthRepository {
    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid
            if (userId != null){
                val userRole = verifyRole(userId)
                Log.d(TAG, "$userRole: ${result.user?.uid}")
                AuthResult.SuccessWithData(userRole)
            } else {
                AuthResult.Failure("Login failed: No User with the ID found")
            }
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Login failed")
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        phoneNumber: String,
        displayName: String
    ): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: java.util.UUID.randomUUID().toString()
            val lifetrackID = generateLifeTrackID()
            val user = User(
                emailAddress = email,
                phoneNumber = phoneNumber,
                lifetrackId = lifetrackID,
                fullName = displayName,
                uuid = userId
            )
            firestore.collection("Patients").document(userId).set(user).await()
            AuthResult.SuccessWithData(userId)
        } catch (e: FirebaseAuthWeakPasswordException) {
            AuthResult.Failure("Password is too weak. Please use a stronger password.")
        } catch (e: FirebaseAuthUserCollisionException) {
            AuthResult.Failure("An account with this email already exists.")
        } catch (e: FirebaseFirestoreException) {
            AuthResult.Failure("Firebase Firestore Error!! ${e.message}")
        } catch (e: Exception) {
            AuthResult.Failure("Technical Registration Failure!! ${e.message}, Please try again later.")
        }
    }

    override fun generateLifeTrackID(): String {
//        val combinedString = "$email:$fUUID"
        val uuid = java.util.UUID.randomUUID()
//        val hash = java.security.MessageDigest
//            .getInstance("SHA-256")
//            .digest(combinedString.toByteArray())
//        val uuid  = hash.fold(0L) { acc, byte -> (acc * 31 + byte.toUByte().toLong()) % 1_000_000_000_000L }
        return "LT_" + uuid.toString().padStart(12, '0')
    }

    override suspend fun verifyRole(userId: String): String {
        var userRole :String = ""
        val db = FirebaseFirestore.getInstance()
        val collections = listOf("Patients", "Practitioners", "Kiongos")

        return try {
            for (collection in collections) {
                val documentSnapshot = db.collection(collection).document(userId).get().await()
                if (documentSnapshot.exists()) {
                    userRole = collection
                    Log.d(TAG, "$collection ***:*** $userRole")
    //                    AuthResult.SuccessWithData(collection)
                }
            }
            userRole
        } catch (e: Exception) {
            AuthResult.Failure("Error verifying role: ${e.message}")
        } as String
    }
}