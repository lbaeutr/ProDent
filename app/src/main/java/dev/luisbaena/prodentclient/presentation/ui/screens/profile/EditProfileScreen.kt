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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.luisbaena.prodentclient.presentation.ui.components.Cabecera
import dev.luisbaena.prodentclient.presentation.ui.components.common.cards.ErrorCard
import dev.luisbaena.prodentclient.presentation.ui.components.common.cards.InfoCard
import dev.luisbaena.prodentclient.presentation.ui.components.common.buttons.PrimaryLoadingButton
import dev.luisbaena.prodentclient.presentation.ui.components.common.buttons.SecondaryButton
import dev.luisbaena.prodentclient.presentation.ui.components.common.dialogs.SuccessDialog
import dev.luisbaena.prodentclient.presentation.viewmodel.AuthViewModel


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
            InfoCard(
                message = "Actualiza tu información personal"
            )

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
                ErrorCard(errorMessage = error)
            }

            // BOTÓN GUARDAR
            PrimaryLoadingButton(
                text = "Guardar Cambios",
                onClick = {
                    isUpdating = true
                    authViewModel.updateProfile(nombre, apellido, email, telefono)
                },
                enabled = nombre.isNotBlank() &&
                        apellido.isNotBlank() &&
                        telefono.isNotBlank() &&
                        email.isNotBlank(),
                isLoading = uiState.isLoading,
                loadingText = "Guardando",
                icon = Icons.Default.Save
            )

            // BOTÓN CANCELAR
            SecondaryButton(
                text = "Cancelar",
                onClick = { navController.popBackStack() },
                enabled = !uiState.isLoading
            )
        }
    }

    // DIÁLOGO DE ÉXITO
    SuccessDialog(
        show = showSuccessDialog && !uiState.isLoading && uiState.errorMessage == null,
        title = "¡Perfil actualizado!",
        message = "Tu información ha sido actualizada correctamente.",
        onConfirm = {
            showSuccessDialog = false
            navController.popBackStack()
        }
    )
}