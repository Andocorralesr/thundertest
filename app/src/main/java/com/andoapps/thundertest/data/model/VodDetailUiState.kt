package com.andoapps.thundertest.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VodDetailUiState(
    val title: String? = null,
    val plot: String? = null,
    val duration: String? = null,
    val releaseDate: String? = null,
    val rating: String? = null,
    val coverUrl: String? = null,
    val isFavorite: Boolean = false
)
