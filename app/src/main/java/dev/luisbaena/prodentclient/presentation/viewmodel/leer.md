# Directorio ViewModel

Este directorio contiene los ViewModels de la aplicación, siguiendo el patrón MVVM (Model-View-ViewModel).

## Archivos

### AuthViewModel.kt
**Función**: Gestiona el estado y lógica de negocio para autenticación y perfil de usuario.

**Estado Manejado**:
- `LoginUiState`: Estado de la UI (loading, success, error, user)
- Autenticación (login/logout)
- Datos del perfil del usuario
- Actualización de perfil
- Cambio de contraseña

**Casos de Uso Integrados**:
- `LoginUseCase`: Autenticación de usuario
- `LogoutUseCase`: Cierre de sesión
- `GetCurrentUserUseCase`: Obtener usuario actual
- `GetProfileUseCase`: Obtener datos del perfil
- `UpdateProfileUseCase`: Actualizar información personal
- `ChangePasswordUseCase`: Cambiar contraseña

**Funciones Principales**:
- `login(email, password)`: Inicia sesión del usuario
- `logout()`: Cierra sesión y limpia el estado
- `refreshProfile()`: Actualiza datos del perfil desde API
- `updateProfile(nombre, apellido, email, telefono)`: Actualiza información del usuario
- `changePassword(newPassword, confirmPassword, onSuccess)`: Cambia la contraseña
- `clearError()`: Limpia mensajes de error
- `resetSuccessState()`: Resetea estado de éxito

**Inyección de Dependencias**: 
- Anotado con `@HiltViewModel`
- Inyección vía constructor con `@Inject`

**Flujo de Datos**:
```
Screen -> ViewModel -> UseCase -> Repository -> API/DataStore
                ↓
            StateFlow (UI observa cambios)
```

## Patrón MVVM

### Responsabilidades del ViewModel:
1. **Gestión de Estado**: Mantiene el estado de la UI en StateFlow
2. **Lógica de Presentación**: Transforma datos del dominio para la UI
3. **Coordinación**: Orquesta múltiples casos de uso
4. **Separación de Concerns**: La UI no conoce detalles de implementación

### Ventajas:
- Testeable (lógica separada de la UI)
- Sobrevive a cambios de configuración (rotación, etc.)
- Un solo punto de verdad para el estado
- Fácil mantenimiento y escalabilidad

## Ejemplo de Uso en Screen:

```kotlin
@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    // UI observa automáticamente cambios de estado
    when {
        uiState.isLoading -> ShowLoading()
        uiState.isSuccess -> NavigateToHome()
        uiState.errorMessage != null -> ShowError(uiState.errorMessage)
    }
}
```

---

**Última actualización**: 2025-10-28

