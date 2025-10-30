package dev.luisbaena.prodentclient.presentation.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.luisbaena.prodentclient.R
import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.presentation.ui.navigation.drawerNavItems
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Navigation Drawer Simple de la aplicación
 */
@Composable
fun AppNavigationDrawer(
    navController: NavController,
    user: User?,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalDrawerSheet(
        modifier = modifier,
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Cabecera del drawer con información del usuario
            DrawerHeader(user = user)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Item: Mi Perfil
            drawerNavItems.forEach { item ->
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
 * Avatar con imagen de drawable + banner fecha/hora
 */
@Composable
private fun DrawerHeader(user: User?) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var currentDate by remember { mutableStateOf(getCurrentDateLong()) }

    // Actualizar cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            currentTime = getCurrentTime()
            currentDate = getCurrentDateLong()
        }
    }

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
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            // Imagen desde drawable
            // TODO: Cambia R.drawable.ic_launcher_foreground por tu imagen
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = "Avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop  // Ajusta la imagen al círculo
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Banner de fecha y hora
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Fecha
                Column {
                    Text(
                        text = "FECHA",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentDate,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                // Divisor vertical
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f))
                )

                // Hora
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "HORA",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentTime,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

/**
 * Obtiene la fecha en formato largo
 * Formato: "Lunes, 30 de Octubre"
 */
private fun getCurrentDateLong(): String {
    val dateFormat = SimpleDateFormat("EEEE, d 'de' MMMM", Locale("es", "ES"))
    return dateFormat.format(Date()).replaceFirstChar { it.uppercase() }
}

/**
 * Obtiene la hora actual
 * Formato: "14:35:22"
 */
private fun getCurrentTime(): String {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return timeFormat.format(Date())
}