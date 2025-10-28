# Directorio Screens

Este directorio contiene todas las pantallas (screens) de la aplicación usando Jetpack Compose.

## Estructura

```
screens/
├── auth/
│   └── LoginScreen.kt
├── home/
│   └── HomeScreen.kt
├── profile/
│   ├── MyProfileScreen.kt
│   ├── EditProfileScreen.kt
│   └── ChangePasswordScreen.kt
├── appointments/
│   └── AppointmentsScreen.kt
├── dentist/
│   └── [Pantallas específicas para dentistas]
└── clinic/
    └── [Pantallas específicas para clínicas]
```

---

## Pantallas por Módulo

### Auth (Autenticación)

#### LoginScreen.kt
**Función**: Pantalla de inicio de sesión.

**Componentes Usados**:
- `LogoScreens`: Logo adaptable
- `CustomTextField`: Campo de email
- `PasswordTextField`: Campo de contraseña con toggle
- `PrimaryLoadingButton`: Botón de login con loading
- `ErrorCard`: Muestra errores de autenticación

**Estado**:
- Email
- Password
- Loading state
- Error messages

**Flujo**:
1. Usuario ingresa credenciales
2. Presiona botón de login
3. ViewModel procesa con LoginUseCase
4. Si éxito → Navega a Main
5. Si error → Muestra ErrorCard

**Navegación**:
- Entrada: Ninguna (primera pantalla)
- Salida: Main (al login exitoso)

---

### Home (Principal)

#### HomeScreen.kt
**Función**: Pantalla principal de la aplicación.

**Componentes Usados**:
- `Cabecera`: TopAppBar con título
- `BottomNavigationBar`: Navegación inferior
- Contenido específico del home

**Estado**:
- Información del dashboard
- Datos del usuario actual

**Características**:
- Bottom navigation visible
- Punto de entrada tras login
- Acceso rápido a funcionalidades principales

---

### Profile (Perfil)

#### MyProfileScreen.kt
**Función**: Vista del perfil del usuario con información personal.

**Componentes Usados**:
- `Cabecera`: TopAppBar
- `BottomNavigationBar`: Navegación inferior
- `ProfileHeader`: Avatar y badge de rol
- `ProfileInfoCard` (x3): Email, teléfono, rol
- `ProfileActionCard` (x2): Editar perfil, cambiar contraseña
- `ConfirmationDialog`: Confirmación de logout

**Estado**:
- Usuario actual (nombre, apellido, email, teléfono, rol)
- Loading state
- Error messages
- Dialog de logout

**Acciones**:
1. **Ver perfil**: Muestra información personal
2. **Editar perfil**: Navega a EditProfileScreen
3. **Cambiar contraseña**: Navega a ChangePasswordScreen
4. **Cerrar sesión**: Muestra dialog → Logout

**Navegación**:
- → EditProfileScreen
- → ChangePasswordScreen
- → Login (después de logout)

#### EditProfileScreen.kt
**Función**: Edición de información personal del usuario.

**Componentes Usados**:
- `Cabecera`: TopAppBar
- `InfoCard`: Información sobre edición
- `CustomTextField` (x4): Nombre, apellido, email, teléfono
- `ErrorCard`: Errores de validación
- `PrimaryLoadingButton`: Botón "Guardar Cambios"
- `SecondaryButton`: Botón "Cancelar"
- `SuccessDialog`: Confirmación de actualización

**Estado**:
- Nombre, apellido, email, teléfono (editables)
- Loading state
- Error messages
- Success dialog

**Validaciones**:
- Campos no vacíos
- Email con formato válido
- Teléfono con formato válido

**Flujo**:
1. Carga datos actuales del perfil
2. Usuario edita campos
3. Presiona "Guardar Cambios"
4. ViewModel valida y actualiza con UpdateProfileUseCase
5. Si éxito → Muestra SuccessDialog → Vuelve a MyProfile
6. Si error → Muestra ErrorCard

**Navegación**:
- Entrada: MyProfileScreen
- Salida: MyProfileScreen (después de guardar o cancelar)

#### ChangePasswordScreen.kt
**Función**: Cambio de contraseña del usuario.

**Componentes Usados**:
- `Cabecera`: TopAppBar
- `InfoCard`: Requisitos de contraseña
- `PasswordTextField` (x2): Nueva contraseña y confirmar
- `ErrorCard`: Errores de validación
- `PrimaryLoadingButton`: Botón "Cambiar Contraseña"
- `SecondaryButton`: Botón "Cancelar"
- `SuccessDialog`: Confirmación de cambio

**Estado**:
- Nueva contraseña
- Confirmar contraseña
- Loading state
- Error messages
- Success dialog

**Validaciones**:
- Mínimo 6 caracteres
- Contraseñas coinciden
- Campos no vacíos

**Flujo**:
1. Usuario ingresa nueva contraseña
2. Confirma la contraseña
3. Presiona "Cambiar Contraseña"
4. ViewModel valida y procesa con ChangePasswordUseCase
5. Si éxito → Muestra SuccessDialog → Vuelve a MyProfile
6. Si error → Muestra ErrorCard

**Navegación**:
- Entrada: MyProfileScreen
- Salida: MyProfileScreen (después de cambiar o cancelar)

---

### Appointments (Citas)

#### AppointmentsScreen.kt
**Función**: Gestión de citas y agenda.

**Componentes Usados**:
- `Cabecera`: TopAppBar
- `BottomNavigationBar`: Navegación inferior
- Lista de citas
- Calendario

**Estado**:
- Lista de citas del usuario
- Filtros de fecha
- Loading state

**Características**:
- Ver citas programadas
- Filtrar por fecha
- Ver detalles de cita
- Crear nueva cita (futuro)

---

### Dentist (Dentista)

**Descripción**: Pantallas específicas para usuarios con rol "dentista".

**Funcionalidades** (a implementar):
- Gestión de pacientes
- Historial clínico
- Agenda de citas
- Tratamientos

---

### Clinic (Clínica)

**Descripción**: Pantallas específicas para usuarios con rol "administrador de clínica".

**Funcionalidades** (a implementar):
- Gestión de dentistas
- Estadísticas
- Configuración de clínica
- Facturación

---

## Patrón de Diseño de Screens

### Estructura Típica:

```kotlin
@Composable
fun MyScreen(
    navController: NavController,
    viewModel: MyViewModel = hiltViewModel()
) {
    // 1. Obtener estado
    val uiState by viewModel.uiState.collectAsState()
    
    // 2. Variables locales de estado
    var localState by remember { mutableStateOf("") }
    
    // 3. Efectos laterales
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate(Routes.NextScreen)
        }
    }
    
    // 4. UI
    Scaffold(
        topBar = { Cabecera("Título") }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            // Contenido
        }
    }
}
```

---

## Mejores Prácticas Implementadas

### 1. State Hoisting
```kotlin
// Estado manejado en ViewModel, no en Screen
val uiState by viewModel.uiState.collectAsState()
```

### 2. Composables Reutilizables
```kotlin
// Usar componentes del directorio common/
PrimaryLoadingButton(...)
ErrorCard(...)
```

### 3. Navegación Type-Safe
```kotlin
// Usar constantes de Routes
navController.navigate(Routes.EditProfile)
```

### 4. Loading States
```kotlin
when {
    uiState.isLoading -> ShowLoading()
    uiState.error != null -> ShowError()
    else -> ShowContent()
}
```

### 5. Efectos con LaunchedEffect
```kotlin
LaunchedEffect(key1 = uiState.isSuccess) {
    // Navegar, mostrar snackbar, etc.
}
```

---

## Navegación entre Screens

### Flujo Principal:

```
Login → Main (Home/Appointments/Profile)
    ↓
Profile → EditProfile
    ↓        ↓
    ← ← ← ← ←
    
Profile → ChangePassword
    ↓        ↓
    ← ← ← ← ←
    
Profile → Logout → Login
```

### Bottom Navigation:

```
Home ↔ Appointments ↔ Profile
(navegación horizontal)
```

---

## Testing de Screens

### UI Tests con Compose:

```kotlin
@Test
fun loginScreen_displaysErrorOnInvalidCredentials() {
    composeTestRule.setContent {
        LoginScreen(navController = mockNavController)
    }
    
    composeTestRule
        .onNodeWithText("Email")
        .performTextInput("invalid")
    
    composeTestRule
        .onNodeWithText("Iniciar Sesión")
        .performClick()
    
    composeTestRule
        .onNodeWithText("Email inválido")
        .assertExists()
}
```

---

## Estadísticas

- **Total Screens**: 6+ implementadas
- **Componentes reutilizados**: 11
- **Navegación**: Type-safe con Routes
- **Estado**: Gestionado con StateFlow
- **Patrón**: MVVM + Clean Architecture

---

**Última actualización**: 2025-10-28
