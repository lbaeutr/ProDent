package dev.luisbaena.prodentclient

// Android framework - Gestión del ciclo de vida de actividades
import android.os.Bundle
// Jetpack Compose - Actividad base optimizada para UI declarativa
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// Android - Habilitación de diseño edge-to-edge (pantalla completa)
import androidx.activity.enableEdgeToEdge
// Jetpack Compose - Componentes de layout y espaciado
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
// Material Design 3 - Componentes de UI modernos
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
// Jetpack Compose - Sistema de reactividad y composición
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// Jetpack Compose - Herramientas de previsualización en desarrollo
import androidx.compose.ui.tooling.preview.Preview
// Tema personalizado de la aplicación
import dev.luisbaena.prodentclient.ui.theme.ProDentClientTheme

/**
 * Actividad principal de la aplicación ProDent Client
 * Punto de entrada de la interfaz de usuario
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita diseño edge-to-edge para aprovechar toda la pantalla
        enableEdgeToEdge()
        setContent {
            ProDentClientTheme {
                // Scaffold proporciona estructura básica con Material Design
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Composable de saludo simple
 * @param name Nombre a mostrar en el saludo
 * @param modifier Modificadores de UI opcionales
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
    )
}

/**
 * Preview para visualización en tiempo de desarrollo
 * Permite ver el composable sin ejecutar la app
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProDentClientTheme {
        Greeting("Android")
    }
}