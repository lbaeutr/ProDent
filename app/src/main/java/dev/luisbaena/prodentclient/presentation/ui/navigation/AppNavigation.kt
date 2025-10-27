package dev.luisbaena.prodentclient.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.luisbaena.prodentclient.presentation.ui.screens.auth.LoginScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.auth.RegisterScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.home.HomeScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.profile.MyProfileScreen
import dev.luisbaena.prodentclient.presentation.viewmodel.AuthViewModel

/*
 * Sistema de Navegación de la App
 * Aquí se define la lógica de navegación entre pantallas
 * y se inicializan las rutas principales.
 */

@Composable
fun AppNavigation() {
    val navController = rememberNavController()  // Controlador de navegación
    val authViewModel: AuthViewModel = hiltViewModel()  // ViewModel global de auth
    val uiState by authViewModel.uiState.collectAsState()  // Estado del usuario

    val animationDuration = 400

    // Pantalla principal según estado de autenticación
    val starDestination = if (uiState.user != null) {
        Routes.Main
    } else {
        Routes.Login
    }

    NavHost(
        navController = navController,
        startDestination = starDestination,

        enterTransition = {
            fadeIn(animationSpec = tween(animationDuration))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(animationDuration))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(animationDuration))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(animationDuration))
        }
    ) {
        // Pantalla de Login
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Main) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }
        // Pantalla de Registro
        composable(Routes.Register) {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Perfil
        composable(Routes.MyProfile) {
            MyProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla Principal
        composable(Routes.Main) {
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Main) { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.MyProfile)
                }
            )
        }

    }
}