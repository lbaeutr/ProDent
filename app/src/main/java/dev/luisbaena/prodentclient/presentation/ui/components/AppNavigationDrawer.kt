package dev.luisbaena.prodentclient.presentation.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.luisbaena.prodentclient.R
import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.presentation.ui.navigation.UserRole
import dev.luisbaena.prodentclient.presentation.ui.navigation.drawerNavItems
import dev.luisbaena.prodentclient.presentation.ui.navigation.getDrawerItemsForRole
import dev.luisbaena.prodentclient.presentation.viewmodel.AuthViewModel

/**
 * Navigation Drawer Simple de la aplicación
 */
@Composable
fun AppNavigationDrawer(
    navController: NavController,
    authViewModel: AuthViewModel,
    onCloseDrawer: () -> Unit = {},
    isDrawerOpen: Boolean = false,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val uiState by authViewModel.uiState.collectAsState()
    val user = uiState.user

    // Determinar el rol del usuario
    val userRole = when {
        user == null -> UserRole.USER
        user.role?.uppercase() == "ADMIN" -> UserRole.ADMIN
        user.role?.uppercase() == "USER" -> UserRole.USER
        else -> UserRole.USER
    }

    ModalDrawerSheet(
        modifier = modifier,
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DrawerHeader(user = user)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtrar items según el rol
            val filteredItems = getDrawerItemsForRole(userRole)

            // Mostrar items filtrados
            filteredItems.forEach { item ->
                val selected = currentRoute == item.route

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                        }
                        onCloseDrawer()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

/**
 * Cabecera del Drawer - Con imagen local
 */
@Composable
fun DrawerHeader(user: User?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar grande con imagen desde drawable
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            // Imagen desde drawable
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = "Avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}