package com.example.randomusers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.randomusers.ui.screen.MainScreen
import com.example.randomusers.ui.screen.ProfileScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen { userId ->
                navController.navigate("profile/$userId")
            }
        }
        composable("profile/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileScreen(userId = userId)
        }
    }
}