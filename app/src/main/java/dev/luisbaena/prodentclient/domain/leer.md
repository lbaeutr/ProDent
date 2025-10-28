# Directorio Domain

Este directorio contiene la lógica de negocio pura de la aplicación (Clean Architecture).

## Estructura

```
domain/
├── model/
│   └── User.kt
├── repository/
│   └── AuthRepository.kt
└── usecase/
    ├── ChangePasswordUseCase.kt
    ├── GetCurrentUserUseCase.kt
    ├── GetProfileUseCase.kt
    ├── LoginUseCase.kt
    ├── LogoutUseCase.kt
    └── UpdateProfileUseCase.kt
```

---

## Models (Entidades de Dominio)

### User.kt
**Función**: Entidad de dominio para usuario (modelo de negocio puro).

**Campos**:
```kotlin
data class User(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val role: String
)
```

**Características**:
- Sin anotaciones de frameworks
- Inmutable (data class)
- Lógica de negocio pura
- Independiente de la capa de datos

---

## Repositories (Interfaces)

### AuthRepository.kt
**Función**: Interface del repositorio de autenticación (contrato).

**Métodos Definidos**:
```kotlin
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): User?
    suspend fun getProfile(): Result<User>
    suspend fun updateProfile(...): Result<User>
    suspend fun changePassword(...): Result<Unit>
}
```

**Ventajas**:
- Inversión de dependencias
- Testeable (mock fácil)
- Separa contrato de implementación
- Permite múltiples implementaciones

---

## Use Cases (Casos de Uso)

### LoginUseCase.kt
**Función**: Caso de uso para proceso de login.

**Responsabilidades**:
1. Validar email
2. Validar contraseña
3. Llamar al repositorio
4. Guardar usuario en preferencias
5. Retornar resultado

**Código**:
```kotlin
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Validaciones
        if (email.isBlank()) return Result.failure(...)
        if (password.length < 6) return Result.failure(...)
        
        // Ejecutar
        return repository.login(email, password)
    }
}
```

### LogoutUseCase.kt
**Función**: Caso de uso para proceso de logout.

**Responsabilidades**:
1. Llamar al repositorio
2. Limpiar preferencias
3. Limpiar token
4. Retornar resultado

### GetCurrentUserUseCase.kt
**Función**: Caso de uso para obtener usuario actual de preferencias.

**Responsabilidades**:
1. Consultar DataStore
2. Retornar usuario o null
3. No hace llamadas a API

### GetProfileUseCase.kt
**Función**: Caso de uso para obtener perfil desde API.

**Responsabilidades**:
1. Llamar al endpoint de perfil
2. Actualizar datos en preferencias
3. Retornar usuario actualizado

### UpdateProfileUseCase.kt
**Función**: Caso de uso para actualizar información personal.

**Responsabilidades**:
1. Validar datos (nombre, email, teléfono)
2. Llamar al endpoint de actualización
3. Actualizar datos en preferencias
4. Retornar usuario actualizado

### ChangePasswordUseCase.kt
**Función**: Caso de uso para cambiar contraseña.

**Responsabilidades**:
1. Validar nueva contraseña (mínimo 6 caracteres)
2. Verificar que coincidan (newPassword == confirmPassword)
3. Llamar al endpoint
4. Retornar resultado

---

## Arquitectura Clean

### Principios Aplicados:

1. **Independencia de Frameworks**: 
   - Sin anotaciones de Retrofit, Room, etc.
   - Solo Kotlin puro

2. **Testeable**:
   - Lógica de negocio aislada
   - Fácil crear mocks
   - Tests unitarios simples

3. **Independencia de UI**:
   - No conoce Compose ni ViewModels
   - Puede funcionar sin UI

4. **Independencia de Base de Datos**:
   - No conoce implementación de DataStore
   - Interface de repositorio

5. **Regla de Dependencia**:
   - Las dependencias apuntan hacia adentro
   - Domain no depende de nada

### Flujo de Dependencias:

```
UI (Compose) 
    ↓
ViewModel
    ↓
UseCase (Domain)
    ↓
Repository Interface (Domain)
    ↑
Repository Implementation (Data)
    ↓
API / DataStore
```

---

## Patrón Use Case

### ¿Por qué usar Use Cases?

1. **Single Responsibility**: Cada caso de uso hace una cosa
2. **Reusabilidad**: Pueden combinarse en diferentes flujos
3. **Testeable**: Lógica aislada y testeada
4. **Mantenibilidad**: Fácil de entender y modificar
5. **Documentación**: El nombre del caso de uso es autodocumentado

### Estructura Típica:

```kotlin
class XxxUseCase @Inject constructor(
    private val repository: XxxRepository
) {
    suspend operator fun invoke(params): Result<T> {
        // 1. Validaciones
        // 2. Lógica de negocio
        // 3. Llamada al repositorio
        // 4. Transformaciones
        // 5. Retornar resultado
    }
}
```

---

## Manejo de Errores

### Result<T> Pattern:

```kotlin
// Éxito
Result.success(user)

// Error
Result.failure(Exception("Email inválido"))

// Uso en ViewModel
when {
    result.isSuccess -> result.getOrNull()
    result.isFailure -> result.exceptionOrNull()?.message
}
```

---

## Testing

### Ejemplo de Test Unitario:

```kotlin
@Test
fun `login with valid credentials returns success`() = runTest {
    // Arrange
    val mockRepository = mock<AuthRepository>()
    val useCase = LoginUseCase(mockRepository)
    whenever(mockRepository.login(any(), any()))
        .thenReturn(Result.success(fakeUser))
    
    // Act
    val result = useCase("test@test.com", "123456")
    
    // Assert
    assertTrue(result.isSuccess)
    assertEquals(fakeUser, result.getOrNull())
}
```

---

## Inyección de Dependencias

Los UseCases se inyectan con Hilt:

```kotlin
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(
        repository: AuthRepository
    ): LoginUseCase = LoginUseCase(repository)
}
```

---

## Ventajas de Clean Architecture

1. **Testeable**: 90%+ cobertura en domain
2. **Mantenible**: Cambios localizados
3. **Escalable**: Fácil añadir nuevas features
4. **Independiente**: Domain no depende de nada
5. **Documentación viva**: Código autodocumentado

---

**Última actualización**: 2025-10-28
