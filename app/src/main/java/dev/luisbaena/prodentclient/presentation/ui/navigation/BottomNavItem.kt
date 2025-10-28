package dev.luisbaena.prodentclient.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
) {
    object Main : BottomNavItem(
        route = Routes.Main,
        title = "Inicio",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    object Search : BottomNavItem(
        route = Routes.Search,
        title = "Buscar",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    object Clinic : BottomNavItem(
        route = Routes.Clinic,
        title = "Clínicas",
        selectedIcon = Icons.Filled.LocalHospital,
        unselectedIcon = Icons.Outlined.LocalHospital
    )

    object MyProfile : BottomNavItem(
        route = Routes.MyProfile,
        title = "Perfil",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}

// Lista para iterar
val bottomNavItems = listOf(
    BottomNavItem.Main,
    BottomNavItem.Search,
    BottomNavItem.Clinic,
    BottomNavItem.MyProfile
)