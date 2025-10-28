# Directorio UI (Presentation Layer)

Este directorio contiene toda la capa de presentación de la aplicación usando Jetpack Compose.

## Estructura Completa

```
ui/
├── components/          → Componentes reutilizables
│   ├── common/          → Componentes comunes modularizados
│   │   ├── inputs/      → TextFields (Custom, Password)
│   │   ├── images/      → LogoScreens
│   │   ├── buttons/     → PrimaryLoading, Secondary
│   │   ├── cards/       → Error, Info
│   │   └── dialogs/     → Success, Confirmation
│   ├── BottomNavigationBar.kt
│   └── Cabecera.kt
│
├── screens/             → Pantallas de la aplicación
│   ├── auth/            → LoginScreen
│   ├── home/            → HomeScreen
│   ├── profile/         → MyProfile, EditProfile, ChangePassword
│   ├── appointments/    → AppointmentsScreen
│   ├── dentist/         → Pantallas de dentista
│   └── clinic/          → Pantallas de clínica
│
├── navigation/          → Configuración de navegación
│   ├── AppNavigation.kt → NavHost principal
│   ├── Routes.kt        → Constantes de rutas
│   └── BottomNavItem.kt → Items de bottom nav
│
├── theme/               → Tema y estilos
│   ├── Color.kt         → Paleta de colores
│   ├── Type.kt          → Tipografía (Lato)
│   └── Theme.kt         → Tema Material 3
│
└── example/             → Ejemplos y demos
    ├── ejemplosDeColores.kt
    └── ejemplosDeTipografia.kt
```

---

## Arquitectura MVVM en UI

### Flujo de Datos:

```
User Input (Screen)
    ↓
ViewModel (Estado + Lógica de Presentación)
    ↓
UseCase (Lógica de Negocio)
    ↓
Repository (Acceso a Datos)
    ↓
API / DataStore
    ↓
← ← ← Response ← ← ←
    ↓
Repository mapea a Domain
    ↓
UseCase retorna Result<T>
    ↓
ViewModel actualiza StateFlow
    ↓
Screen observa y actualiza UI
```

---

## Componentes por Categoría

### 1. Inputs (Entradas)
Ver: `components/leer.md`

- **CustomTextField**: TextField base estilizado
- **PasswordTextField**: Campo con toggle de visibilidad

**Características**:
- MaterialTheme colors
- Bordes redondeados (12.dp)
- Validación con isError
- SupportingText para errores

---

### 2. Buttons (Botones)
Ver: `components/leer.md`

- **PrimaryLoadingButton**: Botón con estado de carga
- **SecondaryButton**: OutlinedButton para acciones secundarias

**Características**:
- CircularProgressIndicator en loading
- Iconos opcionales
- Tipografía titleMedium
- Colores del tema

---

### 3. Cards (Tarjetas)
Ver: `components/leer.md`

- **ErrorCard**: Card roja para errores
- **InfoCard**: Card azul informativa

**Características**:
- Iconos contextuales
- Colores semánticos (error, primary)
- Bordes redondeados
- Padding consistente

---

### 4. Dialogs (Diálogos)
Ver: `components/leer.md`

- **SuccessDialog**: Confirmación de éxito
- **ConfirmationDialog**: Confirmación de acción

**Características**:
- Iconos grandes
- Tipografía headlineSmall
- Botones de acción
- Dismissible/No dismissible

---

### 5. Navigation (Navegación)
Ver: `navigation/leer.md`

- **AppNavigation**: NavHost con todas las rutas
- **Routes**: Constantes type-safe
- **BottomNavItem**: Items de navegación inferior
- **BottomNavigationBar**: Barra de navegación

**Características**:
- Animaciones personalizadas (400ms)
- State preservation
- Single top launch mode
- Badges de notificación

---

### 6. Screens (Pantallas)
Ver: `screens/leer.md`

**Pantallas Implementadas**:
- LoginScreen (auth)
- HomeScreen (home)
- MyProfileScreen (profile)
- EditProfileScreen (profile)
- ChangePasswordScreen (profile)
- AppointmentsScreen (appointments)

**Patrón Común**:
1. ViewModel con StateFlow
2. Componentes reutilizables
3. LaunchedEffect para navegación
4. Scaffold con topBar y content

---

### 7. Theme (Tema)
Ver: `theme/` (archivos individuales)

#### Color.kt
**Paleta ProDent**:
- Primary: ProdentGreen (#26D2A0)
- Secondary: Azul claro
- Error: Rojo
- Background: Blanco/Gris oscuro
- Surface: Cards y contenedores

**Modo Oscuro**: Totalmente soportado

#### Type.kt
**Tipografía Lato**:
- Display: 57sp, 45sp, 36sp (Black)
- Headline: 32sp, 28sp, 24sp (Bold)
- Title: 22sp, 16sp, 14sp (Normal)
- Body: 16sp, 14sp, 12sp (Normal)
- Label: 14sp, 12sp, 11sp (Normal)

#### Theme.kt
**Configuración**:
- Material Design 3
- Colores adaptativos
- Tipografía Lato
- Shapes personalizados

---

## Responsabilidades por Capa

### UI Layer (Este directorio):

1. **Screens**: 
   - Composables de pantalla completa
   - Observan ViewModel
   - Manejan navegación
   - No contienen lógica de negocio

2. **Components**: 
   - Composables reutilizables
   - Sin lógica de negocio
   - Configurables con props
   - MaterialTheme consistent

3. **Navigation**: 
   - Configuración de rutas
   - Animaciones
   - Deep linking (preparado)
   - Bottom navigation

4. **Theme**: 
   - Colores de la marca
   - Tipografía corporativa
   - Shapes y elevaciones
   - Modo oscuro

---

## Estado en Compose

### State Hoisting:

```kotlin
// ✅ Correcto: Estado en ViewModel
@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    MyContent(
        data = uiState.data,
        onAction = { viewModel.handleAction() }
    )
}

// ❌ Incorrecto: Lógica en Composable
@Composable
fun MyScreen() {
    var data by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        // NO hacer llamadas a API aquí
    }
}
```

---

## Composición y Recomposición

### Principios:

1. **Idempotente**: Mismo input → Mismo output
2. **Sin Side Effects**: No modificar estado externo
3. **Rápido**: Recomposiciones frecuentes
4. **Skippable**: Recompone solo lo necesario

### Optimización:

```kotlin
// ✅ Bueno: Estable y skippable
@Composable
fun MyItem(item: Item) {
    Text(item.name)
}

// ⚠️ Cuidado: Lambda crea nueva instancia
@Composable
fun MyItem(item: Item) {
    Button(onClick = { doSomething(item) }) { }
}

// ✅ Mejor: remember para lambdas
@Composable
fun MyItem(item: Item) {
    val onClick = remember(item) { { doSomething(item) } }
    Button(onClick = onClick) { }
}
```

---

## Testing UI

### Compose Test:

```kotlin
@get:Rule
val composeTestRule = createComposeRule()

@Test
fun myScreen_displaysTitle() {
    composeTestRule.setContent {
        MyScreen()
    }
    
    composeTestRule
        .onNodeWithText("Title")
        .assertExists()
        .assertIsDisplayed()
}
```

### Semantics:

```kotlin
// En el composable
Text(
    "Title",
    modifier = Modifier.semantics {
        contentDescription = "Screen title"
    }
)

// En el test
composeTestRule
    .onNode(hasContentDescription("Screen title"))
    .assertExists()
```

---

## Mejores Prácticas Implementadas

### 1. Componentes Pequeños
- Cada composable hace una cosa
- Fácil de entender y mantener
- Reutilizables

### 2. State Hoisting
- Estado en ViewModel
- Composables stateless
- Props para configuración

### 3. Preview Annotations
```kotlin
@Preview(showBackground = true)
@Composable
fun MyComponentPreview() {
    ProdentTheme {
        MyComponent()
    }
}
```

### 4. Material Theme
- Todos usan MaterialTheme
- No colores hardcodeados
- Modo oscuro automático

### 5. Accessibility
- contentDescription en Images
- Semantic properties
- Contraste adecuado

---

## Recursos Externos

### Documentación:
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)

### Guías:
- [Thinking in Compose](https://developer.android.com/jetpack/compose/mental-model)
- [State in Compose](https://developer.android.com/jetpack/compose/state)
- [Testing Compose](https://developer.android.com/jetpack/compose/testing)

---

## Estadísticas del Proyecto

- **Total Screens**: 6+
- **Componentes Comunes**: 11
- **Rutas Definidas**: 5
- **Temas**: Claro y Oscuro
- **Tipografía**: Lato (7 pesos)
- **Colores**: 15+ en paleta

---

## Próximas Mejoras Sugeridas

1. **Más componentes**:
   - LoadingStateBox
   - EmptyStateBox
   - SnackbarHost personalizado

2. **Animaciones**:
   - Transiciones de contenido
   - Animaciones de lista
   - Shared element transitions

3. **Accesibilidad**:
   - TalkBack completo
   - Navegación por teclado
   - Tamaños de fuente escalables

4. **Performance**:
   - LazyColumn optimization
   - derivedStateOf para cálculos
   - remember para lambdas costosas

---

**Última actualización**: 2025-10-28
