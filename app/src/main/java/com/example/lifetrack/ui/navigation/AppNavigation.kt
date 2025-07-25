package com.example.lifetrack.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifetrack.model.network.ApiService
import com.example.lifetrack.model.network.KtorClientFactory
import com.example.lifetrack.model.repository.AuthRepositoryImpl
import com.example.lifetrack.model.repository.UserRepositoryImpl
import com.example.lifetrack.presenter.AuthPresenter
import com.example.lifetrack.presenter.ChatPresenter
import com.example.lifetrack.ui.screens.AboutScreen
import com.example.lifetrack.ui.screens.AdminScreen
import com.example.lifetrack.ui.screens.HomeScreen
import com.example.lifetrack.ui.screens.LoginScreen
import com.example.lifetrack.ui.screens.MenuScreen
import com.example.lifetrack.ui.screens.ProfileScreen
import com.example.lifetrack.ui.screens.RegistrationScreen
import com.example.lifetrack.ui.screens.SplashScreen
import com.example.lifetrack.ui.screens.RestoreScreen
import com.example.lifetrack.ui.screens.MedicalTimelineScreen
import com.example.lifetrack.ui.screens.TelemedicineScreen
import com.example.lifetrack.ui.screens.EpidemicAlertScreen
import com.example.lifetrack.ui.screens.InfoHubScreen
import com.example.lifetrack.ui.screens.AdditionalFeaturesScreen
import com.example.lifetrack.ui.screens.BHPScreen
import com.example.lifetrack.ui.screens.ChatScreen
import com.example.lifetrack.ui.screens.ExpertScreen
import com.example.lifetrack.ui.screens.HelpSupportScreen
import com.example.lifetrack.view.AuthView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
//import kotlinx.coroutines.Coroutine

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
                    popUpTo("login") { inclusive = true }
                }
            }
            override fun onAuthSuccessWithData(data: String) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }

        },
        repository = authRepository,
        scope = scope
    )
    val client = KtorClientFactory().create()
    val apiService = ApiService(client, authRepository)
    val chatPresenter = ChatPresenter(
        apiService = apiService
    )
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(navController, authPresenter)
        }

        composable("signup") {
            RegistrationScreen(
                navController = navController,
                presenter = authPresenter
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
//                onEmergency = { navController.navigate("emergency") },
//                onSearch = { navController.navigate("search")},
//                onAlma = { navController.navigate("alma") }
            )
        }

        composable("chat"){

            ChatScreen(
                navController = navController,
                presenter = chatPresenter)
        }
        composable("profile"){
            ProfileScreen(
                navController = navController,
//                userRepository = userRepository,
//                onLogout = {
//                    scope.launch {
//                        authRepository.logout()
//                        navController.navigate("login") {
//                            popUpTo("home") { inclusive = true }
//                        }
//                    }
//                }
            )
        }

        composable("menu"){
            MenuScreen(
                navController = navController
            )
        }

        composable("reset") {
            RestoreScreen(
                navController = navController,
                userRepository = userRepository
            )
        }
        composable ("kiongozi"){
            AdminScreen(navController)
        }
        composable("expert") { ExpertScreen(navController) }
        composable("medical_timeline") { MedicalTimelineScreen(navController) }
        composable("telemedicine") { TelemedicineScreen(navController) }
        composable("epidemic_alert") { EpidemicAlertScreen(navController) }
        composable("info_hub") { InfoHubScreen(navController) }
        composable("additional_features") { AdditionalFeaturesScreen(navController) }
        composable("help_support") {
             HelpSupportScreen(navController)
        }
        composable("bhp"){
            BHPScreen(navController)
        }
        composable("about") { AboutScreen(navController) }
    }
}