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

### LoginResponseDto.kt
**Función**: Modelo de respuesta del login con token y datos de usuario.

### ProfileResponseDto.kt
**Función**: Modelo de respuesta al obtener perfil del usuario.

### UserDto.kt
**Función**: Modelo de usuario desde la API con función de mapeo a Domain.

### UpdateProfileDto.kt
**Función**: Modelo para actualizar información del perfil.

### ChangePasswordRequestDto.kt
**Función**: Modelo para cambiar contraseña del usuario.

---

## Patrón DTO

### ¿Por qué usar DTOs?

1. **Separación de Concerns**: API puede cambiar sin afectar domain
2. **Flexibilidad**: Nombres de campos diferentes entre API y app
3. **Transformación**: Validación y conversión al mapear

### Flujo de Datos:

```
API Response (JSON) → DTO → Mapper (toDomain()) → Domain Model → ViewModel → UI
```

---

**Última actualización**: 2025-10-28

