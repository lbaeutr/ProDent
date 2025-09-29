package dev.luisbaena.prodentclient.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.domain.usecase.GetCurrentUserUseCase
import dev.luisbaena.prodentclient.domain.usecase.LoginUseCase
import dev.luisbaena.prodentclient.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



/*
 * Funciones principales:
 *  login(): autentica al usuario , actualiza el usuario segun el resultado . EXITO o ERROR.
 *  logout(): cierra la sesión del usuario y resetea el estado de la UI, POR COMPLETO.
 *  clearError(): limpia mensajes de error.
 *  resetSuccessState(): resetea el estado de éxito, útil para evitar acciones repetidas.
 *  checkCurrentUser(): verifica si hay un usuario autenticado al iniciar el ViewModel.
 *
 * ESTE PATRON PERMITE QUE LA UI OBSERVE CAMBIOS DE ESTADO  DE FORMA AUTOMATICA  Y REACCIONE EN CONSECUENCIA.
 */


data class LoginUiState(
    val isLoading: Boolean = false, // indica si hay una operación en curso.
    val isSuccess: Boolean = false, // indica si la operación fue exitosa.
    val errorMessage: String? = null, // almacena mensajes de error.
    val user: User? = null // datos del usuario autenticado.
)


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase

) : ViewModel() {
    // Estado mutable para la UI lo que permite actualizarse
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        checkCurrentUser()
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

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = LoginUiState() // Reseteo completo de la UI "State"
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetSuccessState() {
        _uiState.value = _uiState.value.copy(isSuccess = false)
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