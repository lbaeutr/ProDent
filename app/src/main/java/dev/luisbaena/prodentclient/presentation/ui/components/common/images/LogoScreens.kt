package dev.luisbaena.prodentclient.presentation.ui.components.common.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.luisbaena.prodentclient.R


/*
 * Este componente sirve para establecer el logo en diversas pantallas estableciendolo
 * modo oscuro/claro del sistema de forma auto escalable.
 */

// TODO: tenemos que incorporar el logo tb en XML para que funcione para todos los formatos
@Composable
fun LogoScreens(
    modifier: Modifier = Modifier.size(200.dp),
    contentDescription: String? = "Logo de ProDent"
) {
    Image(
        painter = painterResource(
            id = if (isSystemInDarkTheme()) R.drawable.logo_screen
            else R.drawable.logo_screen
        ),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier,
        alignment = Alignment.Center
    )
}
