package dev.luisbaena.prodentclient.presentation.ui.components.common.inputs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null
                    )
                }
            },
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            enabled = enabled,
            readOnly = readOnly,
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        // Mensaje de error
        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

// ============ PREVIEWS ============

@Preview(showBackground = true, name = "TextField Normal")
@Composable
private fun CustomTextFieldPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("") }
            CustomTextField(
                value = text,
                onValueChange = { text = it },
                label = "Email",
                placeholder = "ejemplo@correo.com",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
    }
}

@Preview(showBackground = true, name = "TextField con valor")
@Composable
private fun CustomTextFieldFilledPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("luis@ejemplo.com") }
            CustomTextField(
                value = text,
                onValueChange = { text = it },
                label = "Email",
                placeholder = "ejemplo@correo.com"
            )
        }
    }
}

@Preview(showBackground = true, name = "TextField con error")
@Composable
private fun CustomTextFieldErrorPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("correo-invalido") }
            CustomTextField(
                value = text,
                onValueChange = { text = it },
                label = "Email",
                placeholder = "ejemplo@correo.com",
                isError = true,
                errorMessage = "El formato del correo no es válido"
            )
        }
    }
}

@Preview(showBackground = true, name = "TextField Password")
@Composable
private fun CustomTextFieldPasswordPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            var password by remember { mutableStateOf("Segura123!") }
            var showPassword by remember { mutableStateOf(false) }

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                placeholder = "Tu contraseña",
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "TextField deshabilitado")
@Composable
private fun CustomTextFieldDisabledPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CustomTextField(
                value = "Campo deshabilitado",
                onValueChange = {},
                label = "Email",
                enabled = false
            )
        }
    }
}

@Preview(showBackground = true, name = "TextField multilinea")
@Composable
private fun CustomTextFieldMultilinePreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            var text by remember { mutableStateOf("Texto de múltiples líneas\nSegunda línea") }
            CustomTextField(
                value = text,
                onValueChange = { text = it },
                label = "Notas",
                placeholder = "Escribe tus notas aquí...",
                singleLine = false,
                maxLines = 5,
                minLines = 3
            )
        }
    }
}
