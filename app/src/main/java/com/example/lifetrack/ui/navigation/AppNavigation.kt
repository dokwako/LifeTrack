package com.example.lifetrack.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifetrack.data.repository.AuthRepositoryImpl
import com.example.lifetrack.data.repository.UserRepositoryImpl
import com.example.lifetrack.presenter.AuthPresenter
import com.example.lifetrack.ui.screens.*
import com.example.lifetrack.view.AuthView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(scope: CoroutineScope) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val userRepository = UserRepositoryImpl()
    val authRepository = AuthRepositoryImpl()
    val authPresenter = AuthPresenter(
        view = object : AuthView {
            override fun showLoading(isLoading: Boolean, message: String?) {
                coroutineScope.launch {
                    if (isLoading) {
                        val defaultMessage = "Loading, please wait..."
                        Toast.makeText(context, message ?: defaultMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun showError(message: String) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
            }
            override fun onAuthSuccess() {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true } // Align with initial flow
                }
            }
        },
        repository = authRepository,
        scope = scope
    )

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                onEmergency = { navController.navigate("login") }, // Redirect to login
                onSearch = { navController.navigate("login") },
                onAlma = { navController.navigate("login") }
            )
        }
        composable("login") {
            LoginScreen(navController, authPresenter)
        }
        composable("signup") {
            RegistrationScreen(navController, authPresenter)
        }
        composable("reset") {
            RestoreScreen(navController, userRepository)
        }
        composable("admin") {
            AdminScreen(navController)
        }
        // Placeholder screens for future implementation
        composable("emergency") {
            PlaceholderScreen(navController, "Emergency Screen")
        }
        composable("search") {
            PlaceholderScreen(navController, "Search Screen")
        }
        composable("alma") {
            PlaceholderScreen(navController, "Alma AI Screen")
        }
    }
}