# Directorio Data

Este directorio contiene la implementación de la capa de datos (Clean Architecture).

## Estructura

```
data/
├── local/
│   └── preferences/
│       └── UserPreferences.kt
├── remote/
│   ├── api/
│   │   └── AuthApiService.kt
│   └── dto/
│       ├── ChangePasswordRequestDto.kt
│       ├── LoginRequestDto.kt
│       ├── LoginResponseDto.kt
│       ├── ProfileResponseDto.kt
│       ├── UpdateProfileDto.kt
│       └── UserDto.kt
└── repository/
    └── AuthRepositoryImpl.kt
```

---

## Local (Almacenamiento Local)

### UserPreferences.kt
**Función**: DataStore para configuraciones y datos del usuario.

**Datos Almacenados**:
- Token JWT de autenticación
- Información del usuario actual
- Preferencias de la app (tema, idioma, etc.)

**Características**:
- Uso de DataStore (Preferences)
- Operaciones asíncronas con Flow
- Type-safe con claves predefinidas
- Persiste entre sesiones

**Ejemplo de Uso**:
```kotlin
class UserPreferences(private val dataStore: DataStore<Preferences>) {
    
    val userFlow: Flow<User?> = dataStore.data.map { prefs ->
        // Recuperar usuario
    }
    
    suspend fun saveUser(user: User) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_NAME] = user.nombre
            // ...
        }
    }
    
    suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }
}
```

---

## Remote (API REST)

Ver documentación detallada en: `remote/leer.md`

### Resumen:
- **AuthApiService**: Endpoints de autenticación y perfil
- **DTOs**: Modelos para comunicación con API
- **Retrofit**: Cliente HTTP configurado
- **Interceptores**: Auth y Logging

---

## Repository (Implementación)

### AuthRepositoryImpl.kt
**Función**: Implementación del contrato AuthRepository.

**Dependencias**:
- `AuthApiService`: Para llamadas a la API
- `UserPreferences`: Para almacenamiento local

**Responsabilidades**:

1. **Login**:
   ```kotlin
   override suspend fun login(email: String, password: String): Result<User> {
       return try {
           val request = LoginRequestDto(email, password)
           val response = apiService.login(request)
           val user = response.data.toDomain()
           
           // Guardar token y usuario
           preferences.saveToken(response.token)
           preferences.saveUser(user)
           
           Result.success(user)
       } catch (e: Exception) {
           Result.failure(e)
       }
   }
   ```

2. **Logout**:
   - Llamar a API para invalidar token
   - Limpiar preferencias locales
   - Limpiar datos en memoria

3. **Get Profile**:
   - Consultar API con token
   - Actualizar preferencias
   - Retornar usuario actualizado

4. **Update Profile**:
   - Enviar datos a API
   - Actualizar preferencias si éxito
   - Retornar usuario actualizado

5. **Change Password**:
   - Enviar nueva contraseña a API
   - No actualiza preferencias (solo contraseña)
   - Retornar resultado

**Patrón Repository**:
```
Domain (Interface) ← Repository Implementation → Data Sources
    ↑                                                ↓
UseCase                                    API + DataStore
```

---

## Flujo de Datos Completo

### Login:
```
1. UI (LoginScreen)
    ↓
2. ViewModel (AuthViewModel)
    ↓
3. UseCase (LoginUseCase)
    ↓
4. Repository Interface (AuthRepository)
    ↓
5. Repository Impl (AuthRepositoryImpl)
    ↓
6. API Service (AuthApiService) → API REST
    ↓
7. DTO Response → Domain Model
    ↓
8. Save to DataStore (UserPreferences)
    ↓
9. Return Result<User>
    ↑
10. Update UI State
```

### Get Current User (Offline):
```
1. UseCase (GetCurrentUserUseCase)
    ↓
2. Repository (getCurrentUser)
    ↓
3. DataStore (UserPreferences)
    ↓
4. Return User? (from local cache)
```

---

## Manejo de Errores

### Tipos de Errores Manejados:

1. **Network Errors**:
   ```kotlin
   catch (e: IOException) {
       Result.failure(Exception("Error de conexión"))
   }
   ```

2. **HTTP Errors**:
   ```kotlin
   catch (e: HttpException) {
       when (e.code()) {
           401 -> Exception("No autorizado")
           404 -> Exception("Usuario no encontrado")
           500 -> Exception("Error del servidor")
       }
   }
   ```

3. **Parsing Errors**:
   ```kotlin
   catch (e: JsonSyntaxException) {
       Result.failure(Exception("Error al procesar respuesta"))
   }
   ```

4. **Custom Errors**:
   ```kotlin
   if (response.data == null) {
       Result.failure(Exception("Datos vacíos"))
   }
   ```

---

## Inyección de Dependencias

### DataModule.kt (Hilt):

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    
    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: DataStore<Preferences>): UserPreferences {
        return UserPreferences(dataStore)
    }
    
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: AuthApiService,
        preferences: UserPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, preferences)
    }
}
```

---

## Testing

### Repository Tests:

```kotlin
@Test
fun `login saves user to preferences on success`() = runTest {
    // Arrange
    val mockApi = mock<AuthApiService>()
    val mockPrefs = mock<UserPreferences>()
    val repository = AuthRepositoryImpl(mockApi, mockPrefs)
    
    whenever(mockApi.login(any()))
        .thenReturn(fakeLoginResponse)
    
    // Act
    val result = repository.login("test@test.com", "123456")
    
    // Assert
    assertTrue(result.isSuccess)
    verify(mockPrefs).saveUser(any())
    verify(mockPrefs).saveToken(any())
}
```

---

## Ventajas de Esta Arquitectura

1. **Separación de Concerns**: 
   - Local y Remote separados
   - Repository como puente

2. **Testeable**: 
   - Mocks fáciles
   - Tests independientes

3. **Offline-First**: 
   - DataStore como cache
   - Funciona sin conexión

4. **Flexible**: 
   - Fácil cambiar de Retrofit a Ktor
   - Fácil cambiar de DataStore a Room

5. **Mantenible**: 
   - Cada componente tiene una responsabilidad
   - Fácil de extender

---

## Mejores Prácticas Implementadas

1. **Single Source of Truth**: DataStore es la fuente única
2. **Result Pattern**: Manejo funcional de errores
3. **Mappers**: DTO → Domain en repository
4. **Suspend Functions**: Operaciones asíncronas
5. **Dependency Injection**: Hilt para DI

---

**Última actualización**: 2025-10-28
