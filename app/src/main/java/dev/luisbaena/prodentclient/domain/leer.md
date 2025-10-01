    

# Directorio domain

## Estructura de Archivos

### `model/`
- **`User.kt`** - Entidad de dominio para usuario (modelo de negocio puro)

### `repository/`
- **`AuthRepository.kt`** - Interface del repositorio de autenticación (contrato)

### `usecase/`
- **`GetCurrentUserUseCase.kt`** - Caso de uso para obtener usuario actual
- **`LoginUseCase.kt`** - Caso de uso para proceso de login
- **`LogoutUseCase.kt`** - Caso de uso para proceso de logout

## Arquitectura Clean