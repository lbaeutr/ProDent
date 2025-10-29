package dev.luisbaena.prodentclient.presentation.ui.screens.clinic

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.luisbaena.prodentclient.presentation.ui.components.Cabecera
import dev.luisbaena.prodentclient.presentation.ui.components.BottomNavigationBar


// TODO: Implementar la pantalla de clínicas, esto es solo un placeholder
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicScreen(navController: NavController, onOpenDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            Cabecera(
                titulo = "Clínicas",
                showMenuIcon = true,
                onMenuClick = onOpenDrawer
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "🏥",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "VER CLÍNICAS",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "(Por implementar)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}