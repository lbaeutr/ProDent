# Directorio Data/Local

Este directorio contiene todo lo relacionado con almacenamiento local de datos.

## Estructura

```
local/
└── preferencias/
    └── UserPreferences.kt
```

---

## Preferencias (DataStore)

### UserPreferences.kt
**Ubicación**: `preferencias/UserPreferences.kt`

**Función**: Gestión de preferencias del usuario usando DataStore (Preferences).

**Datos Almacenados**:
- Token JWT de autenticación
- Información del usuario (id, nombre, apellido, email, teléfono, rol)
- Configuraciones de la app

**Características**:
- Persistencia asíncrona con Flow
- Type-safe con claves predefinidas
- Operaciones suspend para coroutines
- Sobrevive al cierre de la app

**Operaciones Principales**:

1. **Guardar Token**:
```kotlin
suspend fun saveToken(token: String)
```

2. **Obtener Token**:
```kotlin
fun getToken(): String?
```

3. **Guardar Usuario**:
```kotlin
suspend fun saveUser(user: User)
```

4. **Obtener Usuario**:
```kotlin
val userFlow: Flow<User?>
```

5. **Limpiar Datos** (Logout):
```kotlin
suspend fun clearAll()
```

---

## DataStore vs SharedPreferences

### ¿Por qué DataStore?

1. **Asíncrono**: No bloquea el hilo principal
2. **Type-Safe**: Claves tipadas con Preferences Keys
3. **Transaccional**: Operaciones atómicas
4. **Flow**: Observable con Flow de Kotlin
5. **Coroutines**: Integración nativa

### Comparación:

| Característica | SharedPreferences | DataStore |
|----------------|-------------------|-----------|
| Asíncrono | ❌ No | ✅ Sí |
| Type-Safe | ❌ No | ✅ Sí |
| Observable | ❌ No | ✅ Flow |
| Transaccional | ❌ No | ✅ Sí |
| Coroutines | ❌ No | ✅ Nativo |

---

## Implementación Típica

### Definir Keys:

```kotlin
private val TOKEN_KEY = stringPreferencesKey("auth_token")
private val USER_ID_KEY = intPreferencesKey("user_id")
private val USER_NAME_KEY = stringPreferencesKey("user_name")
```

### Guardar Datos:

```kotlin
suspend fun saveToken(token: String) {
    dataStore.edit { preferences ->
        preferences[TOKEN_KEY] = token
    }
}
```

### Leer Datos:

```kotlin
val tokenFlow: Flow<String?> = dataStore.data
    .map { preferences ->
        preferences[TOKEN_KEY]
    }
```

### Limpiar Datos:

```kotlin
suspend fun clearAll() {
    dataStore.edit { preferences ->
        preferences.clear()
    }
}
```

---

## Uso en Repository

```kotlin
class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): Result<User> {
        val response = apiService.login(LoginRequestDto(email, password))
        
        // Guardar token y usuario
        userPreferences.saveToken(response.token)
        userPreferences.saveUser(response.data.toDomain())
        
        return Result.success(response.data.toDomain())
    }
    
    override suspend fun logout(): Result<Unit> {
        apiService.logout()
        userPreferences.clearAll()
        return Result.success(Unit)
    }
}
```

---

## Ventajas en Nuestra App

1. **Offline-First**: Datos disponibles sin conexión
2. **Fast Start**: Usuario cargado desde cache
3. **Seguridad**: Token almacenado localmente
4. **Reactive**: UI actualizada automáticamente con Flow
5. **Simple**: API clara y concisa

---

## Testing

### Unit Test:

```kotlin
@Test
fun `saveToken stores token correctly`() = runTest {
    val dataStore = createTestDataStore()
    val preferences = UserPreferences(dataStore)
    
    preferences.saveToken("test_token")
    
    val token = preferences.getToken()
    assertEquals("test_token", token)
}
```

---

## Mejores Prácticas Implementadas

1. **Single DataStore Instance**: Singleton en DataModule
2. **Structured Keys**: Constantes para todas las claves
3. **Error Handling**: Try-catch en operaciones críticas
4. **Flow para Observación**: userFlow en lugar de callbacks
5. **Clear on Logout**: Limpieza completa de datos

---

**Última actualización**: 2025-10-28

