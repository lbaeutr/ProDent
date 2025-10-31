package dev.luisbaena.prodentclient.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class UserRole {
    USER,
    ADMIN
}

/**
 * Items del Navigation Drawer
 */
sealed class DrawerNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val allowedRoles: List<UserRole>
) {

    object MyProfile : DrawerNavItem(
        route = Routes.MyProfile,
        title = "Mi Perfil",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        allowedRoles = listOf(UserRole.USER, UserRole.ADMIN)
    )

    object DeleteAccount : DrawerNavItem(
        route = Routes.DeleteAccount,
        title = "Eliminar Cuenta",
        selectedIcon = Icons.Filled.Delete,
        unselectedIcon = Icons.Outlined.Delete,
        allowedRoles = listOf(UserRole.ADMIN) // Solo ADMIN
    )
}

// Lista de todos los items del drawer
val drawerNavItems = listOf(
    DrawerNavItem.MyProfile,
    DrawerNavItem.DeleteAccount
)

// Función para filtrar items según el rol
fun getDrawerItemsForRole(userRole: UserRole): List<DrawerNavItem> {
    return when (userRole) {
        UserRole.ADMIN -> drawerNavItems // ADMIN ve TODOS los items
        UserRole.USER -> drawerNavItems.filter { it.allowedRoles.contains(UserRole.USER) } // USER solo ve los permitidos
    }
}
