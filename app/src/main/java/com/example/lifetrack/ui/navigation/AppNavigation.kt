package com.example.lifetrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifetrack.ui.screens.HomeScreen
import com.example.lifetrack.ui.screens.LoginScreen
import com.example.lifetrack.ui.screens.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash" // first screen to show
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
    }

}