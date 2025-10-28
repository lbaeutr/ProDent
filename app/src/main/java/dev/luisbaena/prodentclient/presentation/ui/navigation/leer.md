

# Directorio Navigation

Este directorio contiene la configuración de navegación de la aplicación usando Jetpack Navigation Compose.

## Archivos

### AppNavigation.kt
**Función**: Configura el NavHost principal y define todas las rutas de la aplicación.

**Responsabilidades**:
- Define el grafo de navegación completo
- Gestiona transiciones entre pantallas
- Controla animaciones de entrada/salida
- Maneja la navegación basada en autenticación

**Rutas Configuradas**:
- `Routes.Login`: Pantalla de inicio de sesión
- `Routes.Main`: Pantalla principal con bottom navigation
- `Routes.MyProfile`: Perfil del usuario
- `Routes.EditProfile`: Edición de perfil
- `Routes.ChangePassword`: Cambio de contraseña

**Características**:
- Animaciones personalizadas (slideIntoContainer, slideOutOfContainer)
- Duración de animación: 400ms
- Manejo de backstack para prevenir loops
- Integración con BottomNavigationBar

### Routes.kt
**Función**: Define constantes para todas las rutas de navegación de la app.

**Rutas Definidas**:
```kotlin
object Routes {
    const val Login = "login"
    const val Main = "main"
    const val MyProfile = "my_profile"
    const val EditProfile = "edit_profile"
    const val ChangePassword = "change_password"
}
```

**Ventajas**:
- Type-safe navigation
- Evita strings mágicos
- Fácil refactorización
- Autocompletado en IDE

### BottomNavItem.kt
**Función**: Define los ítems de la barra de navegación inferior.

**Estructura**:
```kotlin
data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)
```

**Ítems Configurados**:
1. **Home**: Pantalla principal
2. **Appointments**: Citas/Agenda
3. **Profile**: Perfil del usuario

**Características**:
- Iconos filled para seleccionado
- Iconos outlined para no seleccionado
- Soporte para badges de notificación
- Integración con BottomNavigationBar

## Flujo de Navegación

### Autenticación:
```
Login -> (éxito) -> Main (BottomNav)
Logout -> Login (limpia backstack)
```

### Perfil:
```
Main -> MyProfile -> EditProfile -> (volver) -> MyProfile
              -> ChangePassword -> (éxito) -> MyProfile
```

### Bottom Navigation:
```
Main ↔ Appointments ↔ Profile
(navegación horizontal con estado guardado)
```

## Patrón de Navegación

### Principios Aplicados:
1. **Single Activity**: Una actividad, múltiples composables
2. **Type Safety**: Rutas definidas como constantes
3. **State Management**: NavController maneja el backstack
4. **Animation**: Transiciones suaves entre pantallas
5. **Deep Linking**: Preparado para enlaces profundos

### Navegación Segura:
```kotlin
navController.navigate(Routes.MyProfile) {
    popUpTo(Routes.Main) { saveState = true }
    launchSingleTop = true
    restoreState = true
}
```

## Ejemplo de Uso:

```kotlin
// En un composable
val navController = rememberNavController()

AppNavigation(
    navController = navController,
    authViewModel = hiltViewModel()
)

// Para navegar
Button(onClick = { navController.navigate(Routes.EditProfile) }) {
    Text("Editar Perfil")
}
```

## Mejores Prácticas Implementadas:

1. **Evitar loops**: Uso de `popUpTo` y `inclusive`
2. **Estado persistente**: `saveState` y `restoreState`
3. **Single top**: `launchSingleTop = true`
4. **Animaciones**: Transiciones personalizadas
5. **Separación de concerns**: Rutas en archivo separado

---

**Última actualización**: 2025-10-28
