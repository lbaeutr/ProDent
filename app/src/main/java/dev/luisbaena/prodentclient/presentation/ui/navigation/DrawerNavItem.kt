package dev.luisbaena.prodentclient.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Items del Navigation Drawer
 */
sealed class DrawerNavItem(
    val route: String,
    val title: String,
    val subtitle: String? = null,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val allowedRoles: List<UserRole>,
    val section: DrawerSection
) {

    object MyProfile : DrawerNavItem(
        route = Routes.MyProfile,
        title = "Mi Perfil",
        subtitle = "Información de personal",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        allowedRoles = listOf(UserRole.USER, UserRole.ADMIN),
        section = DrawerSection.MY_ACCOUNT
    )

    object Clinic : DrawerNavItem(
        route = Routes.Clinic,
        title = "Clinicas",
        subtitle = "Información de clínicas",
        selectedIcon = Icons.Filled.Apartment,
        unselectedIcon = Icons.Outlined.Apartment,
        allowedRoles = listOf(UserRole.USER, UserRole.ADMIN),
        section = DrawerSection.INFORMATION
    )

    object DeleteAccount : DrawerNavItem(
        route = Routes.DeleteAccount,
        title = "Eliminar Cuenta",
        subtitle = "Eliminar cuenta permanentemente",
        selectedIcon = Icons.Filled.Delete,
        unselectedIcon = Icons.Outlined.Delete,
        allowedRoles = listOf(UserRole.ADMIN),
        section = DrawerSection.ADMINISTRATION
    )
}

/*
 * Gestión de visibilidad según rol
 * Aquí se define qué items son visibles para cada rol de usuario.
 */
enum class UserRole {
    USER,
    ADMIN
}

// Función para filtrar items según el rol
fun getDrawerItemsForRole(userRole: UserRole): List<DrawerNavItem> {
    return when (userRole) {
        UserRole.ADMIN -> drawerNavItems // ADMIN ve TODOS los items
        UserRole.USER -> drawerNavItems.filter { it.allowedRoles.contains(UserRole.USER) } // USER solo ve los permitidos
    }
}

// Lista de todos los items del drawer
val drawerNavItems = listOf(
    DrawerNavItem.MyProfile,
    DrawerNavItem.Clinic,
    DrawerNavItem.DeleteAccount
)

// LAS DOS SIGUIENTES ADICIONES SON NUEVAS Y SIRVEN PARA AGRUPAR POR SECCIÓN

// Enum para las secciones
enum class DrawerSection(val title: String) {
    MY_ACCOUNT("MI CUENTA"),
    ADMINISTRATION("ADMINISTRACIÓN"),
    INFORMATION("INFORMACIÓN")
}

// Función mejorada para agrupar por sección
fun getDrawerItemsBySection(userRole: UserRole): Map<DrawerSection, List<DrawerNavItem>> {
    return getDrawerItemsForRole(userRole)
        .groupBy { it.section }
}