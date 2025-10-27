package dev.luisbaena.prodentclient.presentation.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cabecera(String: String) {
    TopAppBar(
        title = {
            Text(
                text = String,
                fontWeight = FontWeight.Bold
            )
        }
    )
}
