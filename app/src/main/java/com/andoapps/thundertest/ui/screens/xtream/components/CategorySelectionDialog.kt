package com.andoapps.thundertest.ui.screens.xtream.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.andoapps.thundertest.data.model.XtreamCategory

@Composable
fun CategorySelectionDialog(
    categories: List<XtreamCategory>,
    onDismissRequest: () -> Unit,
    onCategorySelected: (String?) -> Unit // Acepta String? para "all"
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Ocupa todo el ancho
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f)) // Fondo translúcido
                .clickable(
                    onClick = onDismissRequest,
                    indication = null, // Sin efecto ripple en el fondo
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(top = 60.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Opción para mostrar todas las categorías
                    item {
                        Text(
                            text = "Todas las categorías",
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCategorySelected("all") }
                                .padding(vertical = 16.dp)
                        )
                    }
                    // Lista del resto de categorías
                    items(categories) { category ->
                        Text(
                            text = category.categoryName,
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCategorySelected(category.categoryId) }
                                .padding(vertical = 16.dp)
                        )
                    }
                }

                // Botón para cerrar
                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .size(56.dp)
                        .background(Color.White.copy(alpha = 0.95f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar diálogo",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}