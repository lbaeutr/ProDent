package dev.luisbaena.prodentclient.presentation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.luisbaena.prodentclient.presentation.ui.components.BottomNavigationBar
import dev.luisbaena.prodentclient.presentation.ui.components.Cabecera


// TODO: Implementar la pantalla de inicio, esto es solo un placeholder
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { Cabecera(titulo = "Inicio") },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🏠 PANTALLA HOME",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "TENEMOS QUE HACER COSAS AQUI DARLE UNA IDEA AL USUARIO DE LOS TRABAJOS",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
        }
}
