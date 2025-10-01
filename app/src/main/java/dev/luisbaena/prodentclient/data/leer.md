

# Directorio de data


## Estructura de Archivos

### `local/preferencias/`
- **`UserPreferences.kt`** - DataStore para configuraciones de usuario (tema, token, idioma)

### `remote/api/`
- **`AuthApiService.kt`** - Retrofit interface para endpoints de autenticación

### `remote/dto/`
- **`LoginRequestDto.kt`** - Modelo para enviar credenciales al servidor
- **`LoginResponseDto.kt`** - Modelo de respuesta del login (token, usuario)
- **`UserDto.kt`** - Modelo de usuario desde la API

### `repository/`
- **`AuthRepositoryImpl.kt`** - Implementación del repositorio de autenticación