# ProDent Client - Aplicación Android

Aplicación cliente Android para el sistema de gestión dental ProDent.

## Tecnologías

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose
- **Arquitectura**: Clean Architecture + MVVM
- **Inyección de Dependencias**: Hilt
- **Navegación**: Jetpack Navigation Compose
- **Tema**: Material Design 3
- **Tipografía**: Lato

## Características

- Autenticación de usuarios (Login/Logout)
- Gestión de perfil de usuario
- Cambio de contraseña
- Navegación inferior con múltiples pantallas
- Modo claro/oscuro adaptativo
- Componentes modularizados y reutilizables

## Estructura del Proyecto

```
app/src/main/java/dev/luisbaena/prodentclient/
├── data/              # Capa de datos
│   ├── remote/        # API y DTOs
│   └── repository/    # Implementación de repositorios
├── domain/            # Capa de dominio
│   ├── model/         # Modelos de dominio
│   ├── repository/    # Interfaces de repositorios
│   └── usecase/       # Casos de uso
├── di/                # Inyección de dependencias (Hilt)
├── presentation/      # Capa de presentación
│   ├── ui/
│   │   ├── components/    # Componentes reutilizables
│   │   ├── screens/       # Pantallas de la app
│   │   ├── navigation/    # Configuración de navegación
│   │   └── theme/         # Tema y estilos
│   └── viewmodel/     # ViewModels
└── ui/theme/          # Configuración de tema Material 3
```

## Componentes Modularizados

El proyecto cuenta con **14 componentes reutilizables**:

### Inputs
- `PasswordTextField` - Campo de contraseña con toggle de visibilidad
- `CustomTextField` - TextField base estilizado

### Cards
- `ErrorCard` - Card de error con icono
- `InfoCard` - Card informativa

### Buttons
- `PrimaryLoadingButton` - Botón con estado de carga
- `SecondaryButton` - Botón secundario

### Dialogs
- `SuccessDialog` - Diálogo de éxito
- `ConfirmationDialog` - Diálogo de confirmación

### Navigation
- `BottomNavigationBar` - Barra de navegación inferior
- `Cabecera` - TopAppBar estilizado
- `LogoScreens` - Logo adaptable

**Ver documentación completa**: [docs/README.md](docs/README.md)

## Instalación y Configuración

### Requisitos
- Android Studio Hedgehog o superior
- JDK 17 o superior
- Android SDK 34
- Gradle 8.0 o superior

### Compilar el Proyecto

```bash
# Clonar el repositorio
git clone [URL_DEL_REPO]
cd ProDent

# Compilar
.\gradlew assembleDebug

# Instalar en dispositivo
.\gradlew installDebug
```

### Ejecutar desde Android Studio

1. Abrir el proyecto en Android Studio
2. Sincronizar Gradle
3. Ejecutar la aplicación (Shift + F10)

## Configuración de API

Configurar la URL base de la API en `data/remote/ApiConfig.kt`:

```kotlin
const val BASE_URL = "https://tu-api.com/"
```

## Pantallas Principales

### Autenticación
- **LoginScreen**: Pantalla de inicio de sesión

### Perfil
- **MyProfileScreen**: Vista del perfil del usuario
- **EditProfileScreen**: Edición de datos personales
- **ChangePasswordScreen**: Cambio de contraseña

### Otras
- **HomeScreen**: Pantalla principal
- **AppointmentsScreen**: Gestión de citas

## Documentación

La documentación completa del proyecto se encuentra en la carpeta `docs/`:

- [Verificación de Tema y Tipografía](docs/01_VERIFICACION_TEMA_TIPOGRAFIA.md)
- [Modularización Completada](docs/02_MODULARIZACION_COMPLETADA.md)
- [Resumen de Componentes](docs/03_RESUMEN_COMPONENTES.md)
- [Checklist del Proyecto](docs/04_CHECKLIST.md)
- [Pasos Siguientes](docs/05_PASOS_SIGUIENTES.md)

## Contribución

### Estándares de Código

- Seguir Clean Architecture
- Usar Compose para UI
- Componentes deben usar MaterialTheme
- Documentar funciones públicas
- Mantener separación de responsabilidades

### Proceso de Desarrollo

1. Crear una rama feature desde `develop`
2. Implementar cambios
3. Ejecutar tests (cuando estén disponibles)
4. Crear Pull Request
5. Code review
6. Merge a `develop`

## Troubleshooting

### Errores comunes

**Error de compilación con Hilt**:
```bash
.\gradlew clean
.\gradlew build
```

**Problemas con caché**:
- File → Invalidate Caches / Restart

**Imports no resueltos**:
- Verificar que Gradle esté sincronizado
- Rebuild del proyecto

## Estado del Proyecto

- **Versión**: 1.0.0
- **Estado**: En desarrollo
- **Última actualización**: 2025-10-28

## Licencia

[Tipo de licencia aquí]

## Contacto

**Desarrollador**: Luis Baena  
**Email**: [tu-email]

---

**Nota**: Para información detallada sobre la modularización de componentes y arquitectura del proyecto, consulta la [documentación completa](docs/README.md).

