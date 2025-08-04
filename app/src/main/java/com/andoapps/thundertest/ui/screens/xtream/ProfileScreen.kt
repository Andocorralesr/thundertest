// ruta: com/andoapps/thundertest/ui/screens/xtream/ProfileScreen.kt
package com.andoapps.thundertest.ui.screens.xtream

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andoapps.thundertest.data.model.ContentItem
import com.andoapps.thundertest.data.model.XtreamContentItem
import com.andoapps.thundertest.data.model.XtreamSection
import com.andoapps.thundertest.ui.components.CategoryChip
import com.andoapps.thundertest.ui.components.ContentCarousel
import com.andoapps.thundertest.ui.components.FeaturedContent
import com.andoapps.thundertest.ui.screens.xtream.components.CategorySelectionDialog

// Helper para convertir nuestro modelo de datos de Xtream al modelo que espera la UI.
fun XtreamContentItem.toContentItem(): ContentItem {
    return ContentItem(
        id = this.id.hashCode(),
        title = this.name ?: "Sin título",
        imageUrl = this.imageUrl ?: "",
        genres = emptyList()
    )
}

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    setTopBar: (String, @Composable () -> Unit) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedSection by remember { mutableStateOf<XtreamSection?>(null) }
    var selectedSubCategoryId by remember { mutableStateOf<String?>("all") }
    var showCategoryDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedSection) {
        selectedSubCategoryId = "all"
    }

    LaunchedEffect(uiState.profileName) {
        if (uiState.profileName.isNotBlank()) {
            setTopBar(uiState.profileName) {}
        }
    }

    if (showCategoryDialog) {
        val categoriesToShow = when(selectedSection) {
            XtreamSection.LIVE -> uiState.liveCategories
            XtreamSection.VOD -> uiState.vodCategories
            XtreamSection.SERIES -> uiState.seriesCategories
            null -> emptyList()
        }
        CategorySelectionDialog(
            categories = categoriesToShow,
            onDismissRequest = { showCategoryDialog = false },
            onCategorySelected = { categoryId ->
                selectedSubCategoryId = categoryId
                showCategoryDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${uiState.error}", color = Color.Red)
                }
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val currentSection = selectedSection
                    if (currentSection == null) {
                        mapOf(
                            XtreamSection.LIVE to "Canales",
                            XtreamSection.VOD to "Películas",
                            XtreamSection.SERIES to "Series"
                        ).forEach { (section, title) ->
                            CategoryChip(
                                text = title,
                                onClick = { selectedSection = section }
                            )
                        }
                    } else {
                        CategoryChip(
                            text = "",
                            icon = Icons.Default.Close,
                            onClick = { selectedSection = null }
                        )
                        val sectionTitle = when(currentSection) {
                            XtreamSection.LIVE -> "Canales"
                            XtreamSection.VOD -> "Películas"
                            XtreamSection.SERIES -> "Series"
                        }
                        CategoryChip(text = sectionTitle, onClick = {})
                        CategoryChip(
                            text = "Todas las categorías",
                            hasDropDown = true,
                            onClick = { showCategoryDialog = true }
                        )
                    }
                }

                val filteredContent = remember(uiState.allContent, selectedSection) {
                    when(selectedSection) {
                        XtreamSection.LIVE -> uiState.allContent.filterIsInstance<XtreamContentItem.Live>()
                        XtreamSection.VOD -> uiState.allContent.filterIsInstance<XtreamContentItem.Vod>()
                        XtreamSection.SERIES -> uiState.allContent.filterIsInstance<XtreamContentItem.Series>()
                        null -> uiState.allContent
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 8.dp)
                ) {
                    // --- CAMBIO CLAVE AQUÍ ---
                    // 1. Contenido Destacado: Buscamos el primer elemento que NO SEA un canal en vivo.
                    val featuredItem = filteredContent.firstOrNull { it !is XtreamContentItem.Live }

                    // Si encontramos un item (película o serie), lo mostramos.
                    // Si solo hay canales, esta sección no se mostrará.
                    if (featuredItem != null) {
                        item {
                            FeaturedContent(item = featuredItem.toContentItem())
                        }
                    }

                    // 2. Carruseles por Categoría (el resto de la lógica no cambia)
                    val contentByCategory = filteredContent.groupBy { it.categoryId }
                    val allCategoriesMap = (uiState.liveCategories + uiState.vodCategories + uiState.seriesCategories)
                        .associateBy({ it.categoryId }, { it.categoryName })

                    val categoriesToShow = if (selectedSubCategoryId == "all" || selectedSubCategoryId == null) {
                        contentByCategory.keys.toList()
                    } else {
                        listOf(selectedSubCategoryId)
                    }

                    items(categoriesToShow) { categoryId ->
                        val itemsForCategory = contentByCategory[categoryId] ?: emptyList()
                        val categoryName = allCategoriesMap[categoryId] ?: "Desconocido"

                        if (itemsForCategory.isNotEmpty()) {
                            ContentCarousel(
                                title = categoryName,
                                items = itemsForCategory.map { it.toContentItem() }
                            )
                        }
                    }
                }
            }
        }
    }
}