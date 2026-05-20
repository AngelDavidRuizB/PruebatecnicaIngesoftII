package com.example.pruebatecnicaingesoftii.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Tablas : Screen("tablas")
    object Localidades : Screen("localidades")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToTablas = { navController.navigate(Screen.Tablas.route) },
                onNavigateToLocalidades = { navController.navigate(Screen.Localidades.route) }
            )
        }
        composable(Screen.Tablas.route) {
            TablasScreen()
        }
        composable(Screen.Localidades.route) {
            LocalidadesScreen()
        }
    }
}
