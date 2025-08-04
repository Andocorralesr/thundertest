package com.andoapps.thundertest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Un TopAppBar modular para toda la aplicación.
 *
 * @param title El título que se mostrará en la barra.
 * @param showBackButton Determina si se debe mostrar el ícono de navegación para "Atrás".
 * @param onNavigateUp La acción a ejecutar cuando se presiona el botón "Atrás".
 * @param actions Un bloque Composable para los íconos de acción que se mostrarán a la derecha.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean,
    onNavigateUp: () -> Unit,
    actions: @Composable () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        // Los colores se definen aquí para mantener la consistencia
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.3f),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        // La lógica para el botón de "Atrás" se basa en los parámetros
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }
            }
        },
        // Las acciones son proporcionadas dinámicamente desde el exterior
        actions = { actions() }
    )
}