package dev.luisbaena.prodentclient.presentation.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.luisbaena.prodentclient.presentation.ui.components.Cabecera
    import dev.luisbaena.prodentclient.presentation.viewmodel.AuthViewModel
import dev.luisbaena.prodentclient.ui.theme.ProdentGreen


// TODO: Modularizar los componentes de este screen si es posible adjuntarlos con los del login que comparten muchos elementos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val user = uiState.user

    var nombre by remember { mutableStateOf(user?.nombre ?: "") }
    var apellido by remember { mutableStateOf(user?.apellido ?: "") }
    var telefono by remember { mutableStateOf(user?.telefono ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }

    // Detectar cuando la actualización termina exitosamente
    LaunchedEffect(uiState.isLoading, uiState.errorMessage) {
        if (isUpdating && !uiState.isLoading && uiState.errorMessage == null) {
            showSuccessDialog = true
            isUpdating = false
        } else if (isUpdating && !uiState.isLoading && uiState.errorMessage != null) {
            isUpdating = false
        }
    }

    Scaffold(
        topBar = {
            Cabecera(
                titulo = "Editar Perfil"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Información
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Actualiza tu información personal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // NOMBRE
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    authViewModel.clearError()
                },
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // APELLIDO
            OutlinedTextField(
                value = apellido,
                onValueChange = {
                    apellido = it
                    authViewModel.clearError()
                },
                label = { Text("Apellido") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    authViewModel.clearError()
                },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // TELÉFONO
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it
                    authViewModel.clearError()
                },
                label = { Text("Teléfono") },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading,
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Error message
            uiState.errorMessage?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // BOTÓN GUARDAR
            Button(
                onClick = {
                    isUpdating = true
                    authViewModel.updateProfile(nombre, apellido, email, telefono)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading &&
                        nombre.isNotBlank() &&
                        apellido.isNotBlank() &&
                        telefono.isNotBlank() &&
                        email.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ProdentGreen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Guardando...")
                } else {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Guardar Cambios",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // BOTÓN CANCELAR
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Cancelar")
            }
        }
    }

    // DIÁLOGO DE ÉXITO
    if (showSuccessDialog && !uiState.isLoading && uiState.errorMessage == null) {
        AlertDialog(
            onDismissRequest = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = ProdentGreen
                )
            },
            title = { Text("¡Perfil actualizado!") },
            text = { Text("Tu información ha sido actualizada correctamente.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ProdentGreen
                    )
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}