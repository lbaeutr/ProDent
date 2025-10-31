package dev.luisbaena.prodentclient.presentation.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Items del Navigation Drawer
 */
sealed class DrawerNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {

    object MyProfile : DrawerNavItem(
        route = Routes.MyProfile,
        title = "Mi Perfil",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )

    object DeleteAccount : DrawerNavItem(
        route = Routes.DeleteAccount,
        title = "Eliminar Cuenta",
        selectedIcon = Icons.Filled.Delete,
        unselectedIcon = Icons.Outlined.Delete
    )
}

// Lista de items del drawer
val drawerNavItems = listOf(
    DrawerNavItem.MyProfile,
    DrawerNavItem.DeleteAccount
)

