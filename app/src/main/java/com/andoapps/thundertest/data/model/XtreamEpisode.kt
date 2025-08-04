// ruta: com/andoapps/thundertest/data/model/XtreamEpisode.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamEpisode(
    val id: String?,
    val title: String?,
    var season: String?, // 'var' para poder asignarla después
    @param:Json(name = "episode_num")         val episodeNum: String?,
    @param:Json(name = "container_extension") val containerExtension: String?,
    val info: XtreamEpisodeInfo?,
    @param:Json(name = "custom_sid")          val customSid: String?,
    val added: String?,
    val url: String?
)