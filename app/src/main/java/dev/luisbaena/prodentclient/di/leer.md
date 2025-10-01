

# Directorio de di (Dependency Injection)

## Estructura de Archivos

### `DataModule.kt`
- **Función:** Módulo Hilt para inyección de dependencias de datos
- **Provee:** Repositorios, DataStore, preferencias de usuario
- **Ejemplo:** `@Provides AuthRepository`, `@Provides UserPreferences`

### `NetworkModule.kt`
- **Función:** Módulo Hilt para inyección de dependencias de red
- **Provee:** Retrofit, OkHttpClient, interceptores, servicios API
- **Ejemplo:** `@Provides AuthApiService`, `@Provides Retrofit`

