package com.andoapps.thundertest.data.model

data class ContentItem(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val isMovie: Boolean = true,
    val genres: List<String> = emptyList(),
    val top10: Boolean = false,
    val year: String? = null // <- AÑADE ESTA LÍNEA
)

