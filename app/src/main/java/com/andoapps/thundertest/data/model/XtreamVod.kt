// ruta: com/andoapps/thundertest/data/model/XtreamVod.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamVod(
    // Dejamos los campos básicos que vienen en la lista principal
    val name: String? = null,
    @param:Json(name = "stream_id")           val streamId: Int? = null,
    @param:Json(name = "stream_icon")         var streamIcon: String? = null, // 'var' para poder modificarlo después
    @param:Json(name = "category_id")         val categoryId: String? = null,
    @param:Json(name = "container_extension") val containerExtension: String? = null,

    // Estos campos se llenarán después al ver los detalles
    var plot: String? = null,
    var rating: Double? = null,
    var releaseDate: String? = null,
    var duration: String? = null
)