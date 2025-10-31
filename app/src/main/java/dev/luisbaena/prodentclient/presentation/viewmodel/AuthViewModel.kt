package dev.luisbaena.prodentclient.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.domain.usecase.ChangePasswordUseCase
import dev.luisbaena.prodentclient.domain.usecase.DeleteAccountByEmailUseCase
import dev.luisbaena.prodentclient.domain.usecase.GetCurrentUserUseCase
import dev.luisbaena.prodentclient.domain.usecase.GetProfileUseCase
import dev.luisbaena.prodentclient.domain.usecase.LoginUseCase
import dev.luisbaena.prodentclient.domain.usecase.LogoutUseCase
import dev.luisbaena.prodentclient.domain.usecase.UpdateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
 * Funciones principales:
 *  login(): autentica al usuario , actualiza el usuario segun el resultado . EXITO o ERROR.
 *  logout(): cierra la sesión del usuario y resetea el estado de la UI, POR COMPLETO, anadido un callback opcional.
 *  clearError(): limpia mensajes de error.
 *  resetSuccessState(): resetea el estado de éxito, útil para evitar acciones repetidas.
 *  checkCurrentUser(): verifica si hay un usuario autenticado al iniciar el ViewModel.
 *  refreshProfile(): actualiza los datos del perfil del usuario desde la API.
 *  updateProfile(): actualiza los datos del perfil del usuario.
 *  changePassword(): cambia la contraseña del usuario.
 *
 * ESTE PATRON PERMITE QUE LA UI OBSERVE CAMBIOS DE ESTADO  DE FORMA AUTOMATICA  Y REACCIONE EN CONSECUENCIA.
 */


data class LoginUiState(
    val isLoading: Boolean = false, // indica si hay una operación en curso.
    val isSuccess: Boolean = false, // indica si la operación fue exitosa.
    val errorMessage: String? = null, // almacena mensajes de error.
    val user: User? = null, // datos del usuario autenticado.
    val isAccountDeleted: Boolean = false // indica si la cuenta ha sido eliminada.
)


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val deleteAccountByEmailUseCase: DeleteAccountByEmailUseCase
) : ViewModel() {
    // Estado mutable para la UI lo que permite actualizarse
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            checkCurrentUser()
        }
    }

    fun login(email: String, password: String) {

        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, errorMessage = null, isSuccess = false)


            val result = loginUseCase(email.trim(), password)

            if (result.isSuccess) {
                val user = result.getOrNull()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    user = user,
                    errorMessage = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido",
                )
            }
        }
    }

    // Refrescar datos del perfil desde API
    fun refreshProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = getProfileUseCase()

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = result.getOrNull(),
                    errorMessage = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    // Actualizar perfil
    fun updateProfile(nombre: String, apellido: String, email: String, telefono: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val result = updateProfileUseCase(nombre, apellido, email, telefono)

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = result.getOrNull(),
                    errorMessage = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    //Cambiar contraseña
    fun changePassword(newPassword: String, confirmPassword: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val result = changePasswordUseCase(newPassword, confirmPassword)

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = null
                )
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    // Función para que admin elimine cuenta por email
    fun deleteAccountByEmail(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            deleteAccountByEmailUseCase(email).fold(
                onSuccess = { message ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isAccountDeleted = true
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            )
        }
    }

    fun resetAccountDeletedState() {
        _uiState.value = _uiState.value.copy(isAccountDeleted = false)
    }

    fun logout(onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = LoginUiState()
            onComplete() //es opcional y normalmente se usa para navegar o hacer acciones después de cerrar sesión.
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {

            val user = getCurrentUserUseCase()
            if (user != null) {
                _uiState.value = _uiState.value.copy(user = user, isSuccess = true)
            }
        }
    }

}