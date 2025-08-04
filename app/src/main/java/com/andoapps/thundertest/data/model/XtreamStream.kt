// ruta: com/andoapps/thundertest/data/model/XtreamStream.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamStream(
    val name: String? = null,

    @param:Json(name = "stream_id")
    val streamId: Int? = null,

    @param:Json(name = "stream_icon")
    val streamIcon: String? = null,

    @param:Json(name = "category_id")
    val categoryId: String? = null,

    // ————————————————+
    // Agregado para soportar EPG directo de Xtream
    @param:Json(name = "epg_channel_id")
    val epgChannelId: String? = null
    // ————————————————+
)
