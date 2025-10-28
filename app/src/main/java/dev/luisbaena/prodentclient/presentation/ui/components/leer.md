# Directorio Components

Este directorio contiene todos los componentes UI reutilizables de la aplicación.

## Estructura

```
components/
├── BottomNavigationBar.kt    [Pre-existente]
├── Cabecera.kt                [Pre-existente]
└── common/
    ├── inputs/
    │   ├── CustomTextField.kt         [Pre-existente]
    │   └── PasswordTextField.kt       [Nuevo]
    ├── images/
    │   └── LogoScreens.kt             [Pre-existente]
    ├── buttons/
    │   ├── PrimaryLoadingButton.kt    [Nuevo]
    │   └── SecondaryButton.kt         [Nuevo]
    ├── cards/
    │   ├── ErrorCard.kt               [Nuevo]
    │   └── InfoCard.kt                [Nuevo]
    └── dialogs/
        ├── SuccessDialog.kt           [Nuevo]
        └── ConfirmationDialog.kt      [Nuevo]
```

## Componentes Principales

### BottomNavigationBar.kt [Pre-existente]
**Función**: Barra de navegación inferior con animaciones.

**Características**:
- Navegación entre pantallas principales
- Iconos filled/outlined según selección
- Soporte para badges de notificación
- Animaciones de entrada/salida (slideInVertically/slideOutVertically)
- Colores adaptativos al tema

**Props**: `navController`, `modifier`, `visible`

**Pantallas**: HomeScreen, AppointmentsScreen, MyProfileScreen

---

### Cabecera.kt [Pre-existente]
**Función**: TopAppBar estilizado con colores del tema.

**Características**:
- Tipografía bold consistente
- Colores personalizables
- Adaptable a tema claro/oscuro
- Material Design 3

**Props**: `titulo`, `modifier`, `colors`

**Pantallas**: Todas las pantallas internas (excepto Login)

---

## Componentes Common

### Inputs

#### CustomTextField.kt [Pre-existente]
**Función**: TextField base estilizado.

**Props**: `value`, `onValueChange`, `label`, `placeholder`, `keyboardType`, `visualTransformation`, `enabled`, `singleLine`, `trailingIcon`

**Uso**: LoginScreen, EditProfileScreen

**Características**:
- Bordes redondeados (12.dp)
- Colores del tema
- Configurable y versátil

#### PasswordTextField.kt [Nuevo]
**Función**: Campo de contraseña con toggle de visibilidad.

**Props**: `value`, `onValueChange`, `label`, `placeholder`, `enabled`, `isError`, `supportingText`, `leadingIcon`

**Uso**: LoginScreen, ChangePasswordScreen

**Características**:
- Toggle mostrar/ocultar contraseña
- Iconos de visibilidad
- Validación con isError
- Texto de soporte para errores

---

### Images

#### LogoScreens.kt [Pre-existente]
**Función**: Logo adaptable a tema claro/oscuro.

**Props**: `modifier`, `contentDescription`

**Uso**: LoginScreen

**Características**:
- Auto-escalable
- Adaptativo al tema del sistema
- ContentScale.Fit

---

### Buttons

#### PrimaryLoadingButton.kt [Nuevo]
**Función**: Botón principal con estado de carga.

**Props**: `text`, `onClick`, `enabled`, `isLoading`, `loadingText`, `icon`, `containerColor`, `contentColor`

**Uso**: LoginScreen, ChangePasswordScreen, EditProfileScreen

**Características**:
- CircularProgressIndicator durante carga
- Icono opcional
- Colores personalizables
- Tipografía titleMedium Bold
- MaterialTheme por defecto

#### SecondaryButton.kt [Nuevo]
**Función**: Botón secundario (OutlinedButton).

**Props**: `text`, `onClick`, `enabled`

**Uso**: ChangePasswordScreen, EditProfileScreen

**Características**:
- Estilo outlined
- Tipografía titleMedium Medium
- Colores del tema

---

### Cards

#### ErrorCard.kt [Nuevo]
**Función**: Card de error con icono de advertencia.

**Props**: `errorMessage`, `icon`

**Uso**: LoginScreen, ChangePasswordScreen, EditProfileScreen

**Características**:
- Fondo errorContainer
- Icono Warning por defecto
- Tipografía bodyMedium
- Bordes redondeados (8.dp)

#### InfoCard.kt [Nuevo]
**Función**: Card informativa azul.

**Props**: `message`, `icon`

**Uso**: ChangePasswordScreen, EditProfileScreen

**Características**:
- Fondo primaryContainer
- Icono Info por defecto
- Tipografía bodyMedium
- Bordes redondeados (12.dp)

---

### Dialogs

#### SuccessDialog.kt [Nuevo]
**Función**: Diálogo de éxito con icono verde.

**Props**: `show`, `title`, `message`, `onConfirm`, `icon`, `iconTint`, `confirmButtonText`, `confirmButtonColor`

**Uso**: ChangePasswordScreen, EditProfileScreen

**Características**:
- Icono CheckCircle
- Color primary por defecto
- Tipografía headlineSmall (título)
- Tipografía bodyMedium (mensaje)
- No dismissible (sin cerrar por fuera)

#### ConfirmationDialog.kt [Nuevo]
**Función**: Diálogo de confirmación con dos botones.

**Props**: `show`, `title`, `message`, `onConfirm`, `onDismiss`, `icon`, `iconTint`, `confirmButtonText`, `dismissButtonText`, `confirmButtonColor`

**Uso**: MyProfileScreen (logout)

**Características**:
- Icono Logout por defecto
- Color error por defecto
- Botón confirmar y cancelar
- Dismissible
- Tipografía headlineSmall y bodyMedium

---

## Principios de Diseño

### 1. Reutilización
- Componentes usados en múltiples pantallas
- Props configurables para flexibilidad
- Defaults sensatos

### 2. MaterialTheme
- Todos usan `MaterialTheme.colorScheme`
- Todos usan `MaterialTheme.typography`
- Adaptables a tema claro/oscuro

### 3. Consistencia
- Mismo estilo en toda la app
- Bordes redondeados consistentes
- Espaciados uniformes

### 4. Accesibilidad
- contentDescription en iconos
- Colores de alto contraste
- Tipografía legible (Lato)

### 5. Mantenibilidad
- Código DRY (Don't Repeat Yourself)
- Cambios centralizados
- Fácil de actualizar

---

## Estadísticas

- **Total componentes**: 11 reutilizables
- **Pre-existentes**: 3
- **Nuevos (modularizados)**: 8
- **Reutilización**: 20+ veces
- **Pantallas usando componentes**: 4

---

## Documentación Completa

Ver documentación detallada en: `docs/02_MODULARIZACION_COMPLETADA.md`

---

**Última actualización**: 2025-10-28
