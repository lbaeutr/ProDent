//package dev.luisbaena.prodentclient.presentation.ui.navigation
//
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import dev.luisbaena.prodentclient.presentation.ui.screens.admin.DeleteAccountScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.auth.LoginScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.auth.RegisterScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.clinic.ClinicScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.home.HomeScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.profile.ChangePasswordScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.profile.EditProfileScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.profile.MyProfileScreen
//import dev.luisbaena.prodentclient.presentation.ui.screens.search.SearchScreen
//import dev.luisbaena.prodentclient.presentation.viewmodel.AuthViewModel
//import kotlinx.coroutines.launch
//
///*
// * Sistema de Navegación de la App
// * Aquí se define la lógica de navegación entre pantallas
// * y se inicializan las rutas principales.
// */
//
//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()  // Controlador de navegación
//    val authViewModel: AuthViewModel = hiltViewModel()  // ViewModel global de auth
//    val uiState by authViewModel.uiState.collectAsState()  // Estado del usuario
//
//    val animationDuration = 400
//
//    // Pantalla principal según estado de autenticación
//    val starDestination = if (uiState.user != null) {
//        Routes.Main
//    } else {
//        Routes.Login
//    }
//
//    // Estado del drawer
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    // Función para abrir el drawer
//    val openDrawer: () -> Unit = {
//        scope.launch {
//            drawerState.open()
//        }
//    }
//
//
//    NavHost(
//        navController = navController,
//        startDestination = starDestination,
//
//        enterTransition = {
//            fadeIn(animationSpec = tween(animationDuration))
//        },
//        exitTransition = {
//            fadeOut(animationSpec = tween(animationDuration))
//        },
//        popEnterTransition = {
//            fadeIn(animationSpec = tween(animationDuration))
//        },
//        popExitTransition = {
//            fadeOut(animationSpec = tween(animationDuration))
//        }
//    ) {
//        // Pantalla de Login
//        composable(Routes.Login) {
//            LoginScreen(
//                onLoginSuccess = {
//                    navController.navigate(Routes.Main) {
//                        popUpTo(Routes.Login) { inclusive = true }
//                    }
//                }
//            )
//        }
//        // Pantalla de Registro
//        composable(Routes.Register) {
//            RegisterScreen(
//                onNavigateBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        // Pantalla de Perfil
//        composable(Routes.MyProfile) {
//            MyProfileScreen(
//                navController = navController,
//                onLogout = {
//                    authViewModel.logout {
//                        navController.navigate(Routes.Login) {
//                            popUpTo(0) { inclusive = true }
//                        }
//                    }
//                }
//            )
//        }
//
//        // Editar Perfil
//        composable(Routes.EditProfile) {
//            EditProfileScreen(navController = navController)
//        }
//
//        // Cambiar Contraseña
//        composable(Routes.ChangePassword) {
//            ChangePasswordScreen(navController = navController)
//        }
//        composable(Routes.DeleteAccount) {
//             DeleteAccountScreen(navController = navController)
//        }
//
//        // Pantalla Principal
//        composable(Routes.Main) {
//            HomeScreen(
//                navController = navController
//            )
//        }
//
//        // Pantalla de Clínica
//        composable(Routes.Clinic) {
//            ClinicScreen(
//                navController = navController
//
//            )
//        }
//
//        // Pantalla de Búsquedaq
//        composable(Routes.Search) {
//            SearchScreen(
//                navController = navController
//            )
//        }
//
//
//    }
//}

package dev.luisbaena.prodentclient.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.luisbaena.prodentclient.presentation.ui.components.AppNavigationDrawer
import dev.luisbaena.prodentclient.presentation.ui.screens.admin.DeleteAccountScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.auth.LoginScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.auth.RegisterScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.clinic.ClinicScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.home.HomeScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.profile.ChangePasswordScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.profile.EditProfileScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.profile.MyProfileScreen
import dev.luisbaena.prodentclient.presentation.ui.screens.search.SearchScreen
import dev.luisbaena.prodentclient.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

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

    val animationDuration = 400 //TODO: Ajustar duración de animaciones

    // Estado del drawer
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Función para abrir el drawer
    val openDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }

    // Observar la ruta actual para gestionar el drawer
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Rutas que NO tienen drawer ni bottom bar
    val routesWithoutNavigation = listOf(Routes.Login, Routes.Register)
    val showNavigation = currentRoute !in routesWithoutNavigation

    // Navegar automáticamente a Main si el usuario ya está autenticado
    LaunchedEffect(uiState.user) {
        if (uiState.user != null && currentRoute == Routes.Login) {
            navController.navigate(Routes.Main) {
                popUpTo(Routes.Login) { inclusive = true }
            }
        }
    }

    // Drawer envuelve todo
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppNavigationDrawer(
                navController = navController,
                user = uiState.user,
                onCloseDrawer = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        },
        gesturesEnabled = showNavigation  //  activo en pantallas autenticadas
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Login, // Siempre inicia en Login
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
                    navController = navController,
                    onLogout = {
                        authViewModel.logout {
                            navController.navigate(Routes.Login) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    onOpenDrawer = openDrawer
                )
            }

            // Editar Perfil
            composable(Routes.EditProfile) {
                EditProfileScreen(
                    navController = navController,
                    onOpenDrawer = openDrawer
                )
            }

            // Cambiar Contraseña
            composable(Routes.ChangePassword) {
                ChangePasswordScreen(
                    navController = navController,
                    onOpenDrawer = openDrawer
                )
            }

            composable(Routes.DeleteAccount) {
                DeleteAccountScreen(
                    navController = navController,
                    onOpenDrawer = openDrawer
                )
            }

            // Pantalla Principal
            composable(Routes.Main) {
                HomeScreen(
                    navController = navController,
                    onOpenDrawer = openDrawer
                )
            }

            // Pantalla de Clínica
            composable(Routes.Clinic) {
                ClinicScreen(
                    navController = navController,
                    onOpenDrawer = openDrawer
                )
            }

            // Pantalla de Búsqueda
            composable(Routes.Search) {
                SearchScreen(
                    navController = navController,
                    onOpenDrawer = openDrawer
                )
            }
        }
    }
}