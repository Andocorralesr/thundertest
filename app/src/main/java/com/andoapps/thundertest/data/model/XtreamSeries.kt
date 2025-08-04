// ruta: com/andoapps/thundertest/data/model/XtreamSeries.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamSeries(
    val name: String? = null,
    @param:Json(name = "series_id")   val seriesId: Int,
    val cover: String? = null,
    @param:Json(name = "category_id") val categoryId: String? = null,
    val plot: String? = null,
    val rating: Double? = null,
    val releaseDate: String? = null
)