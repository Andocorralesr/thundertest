// ruta: com/andoapps/thundertest/data/model/XtreamEpisodeInfo.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamEpisodeInfo(
    val duration: String?,
    val plot: String?,
    val rating: String?,
    @param:Json(name = "releasedate") val releaseDate: String?, // Corregimos el nombre
    val name: String?,
    @param:Json(name = "movie_image") val movieImage: String?
)