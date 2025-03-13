package com.example.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("welcome/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "User"
            RequestLocationPermission(context) {
                WelcomeScreen(username)
            }
        }
    }
}
