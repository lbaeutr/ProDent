# Directorio Data/Remote

Este directorio contiene todo lo relacionado con comunicación remota (API REST).

## Estructura

```
remote/
├── api/
│   └── AuthApiService.kt
└── dto/
    ├── ChangePasswordRequestDto.kt
    ├── LoginRequestDto.kt
    ├── LoginResponseDto.kt
    ├── ProfileResponseDto.kt
    ├── UpdateProfileDto.kt
    └── UserDto.kt
```

---

## API Services

### AuthApiService.kt
**Función**: Define los endpoints de la API REST para autenticación y perfil.

**Endpoints Definidos**:

```kotlin
@POST("auth/login")
suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

@POST("auth/logout")
suspend fun logout()

@GET("profile")
suspend fun getProfile(): ProfileResponseDto

@PUT("profile")
suspend fun updateProfile(@Body request: UpdateProfileDto): ProfileResponseDto

@PUT("profile/password")
suspend fun changePassword(@Body request: ChangePasswordRequestDto): Response<Unit>
```

**Características**:
- Anotaciones Retrofit
- Funciones suspend para coroutines
- Type-safe requests y responses
- Manejo de errores con Response<T>

---

## DTOs (Data Transfer Objects)

### LoginRequestDto.kt
**Función**: Modelo para enviar credenciales al servidor.

**Campos**:
```kotlin
{
  "email": "usuario@ejemplo.com",
  "password": "contraseña123"
}
```

### LoginResponseDto.kt
**Función**: Modelo de respuesta del login.

**Campos**:
```kotlin
{
  "token": "jwt_token_aqui",
  "data": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@ejemplo.com",
    "telefono": "123456789",
    "role": "paciente"
  }
}
```

### ProfileResponseDto.kt
**Función**: Modelo de respuesta al obtener perfil.

**Campos**:
```kotlin
{
  "data": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@ejemplo.com",
    "telefono": "123456789",
    "role": "paciente"
  }
}
```

### UserDto.kt
**Función**: Modelo de usuario desde la API.

**Campos**:
```kotlin
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com",
  "telefono": "123456789",
  "role": "paciente"
}
```

**Función de Mapeo**:
```kotlin
fun UserDto.toDomain(): User {
    return User(
        id = id,
        nombre = nombre,
        apellido = apellido,
        email = email,
        telefono = telefono,
        role = role
    )
}
```

### UpdateProfileDto.kt
**Función**: Modelo para actualizar información del perfil.

**Campos**:
```kotlin
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@ejemplo.com",
  "telefono": "123456789"
}
```

### ChangePasswordRequestDto.kt
**Función**: Modelo para cambiar contraseña.

**Campos**:
```kotlin
{
  "newPassword": "nuevaContraseña123",
  "confirmPassword": "nuevaContraseña123"
}
```

---

## Patrón DTO

### ¿Por qué usar DTOs?

1. **Separación de Concerns**: 
   - API puede cambiar sin afectar domain
   - Domain models puros sin anotaciones de serialización

2. **Flexibilidad**:
   - Nombres de campos diferentes (snake_case API vs camelCase Kotlin)
   - Campos opcionales en API pero requeridos en domain

3. **Transformación**:
   - Validación al mapear
   - Cálculos derivados
   - Conversión de tipos

### Flujo de Datos:

```
API Response (JSON)
    ↓
DTO (data class con @Serializable)
    ↓
Mapper (toDomain())
    ↓
Domain Model (User)
    ↓
ViewModel
    ↓
UI (Screen)
```

---

## Configuración Retrofit

Los DTOs se usan con Retrofit configurado en `NetworkModule.kt`:

```kotlin
Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()
```

### Interceptores:
- **AuthInterceptor**: Añade token JWT a las peticiones
- **LoggingInterceptor**: Log de requests/responses (solo debug)

---

## Manejo de Errores

### Tipos de Errores:

1. **Network Error**: Sin conexión
2. **HTTP Error**: 4xx, 5xx
3. **Parsing Error**: JSON inválido
4. **Timeout**: Servidor no responde

### Estrategia:
```kotlin
try {
    val response = apiService.login(request)
    Result.success(response.data.toDomain())
} catch (e: Exception) {
    Result.failure(e)
}
```

---

## Mejores Prácticas Implementadas

1. **Suspend Functions**: Para operaciones asíncronas
2. **Result<T>**: Para manejo de errores funcional
3. **Type Safety**: DTOs fuertemente tipados
4. **Mappers**: Transformación explícita DTO -> Domain
5. **Serialización**: @Serializable para Gson/Kotlinx
6. **Immutability**: data classes inmutables

---

## Testing

### Unit Tests:
```kotlin
@Test
fun `UserDto toDomain maps correctly`() {
    val dto = UserDto(1, "Juan", "Pérez", "juan@test.com", "123", "paciente")
    val domain = dto.toDomain()
    
    assertEquals("Juan", domain.nombre)
    assertEquals("paciente", domain.role)
}
```

---

**Última actualización**: 2025-10-28

