package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Representa la respuesta completa de la API para la acción "get_vod_info".
 * Contiene un objeto 'info' y un objeto 'movie_data'.
 */
@JsonClass(generateAdapter = true)
data class XtreamVodInfoResponse(
    val info: XtreamEpisodeInfo?, // Reutilizamos XtreamEpisodeInfo para los detalles
    @param:Json(name = "movie_data") val movieData: XtreamVod?
)

/**
 * Representa la respuesta completa de la API para la acción "get_series_info".
 * Contiene información de la serie y un mapa de episodios.
 */
@JsonClass(generateAdapter = true)
data class XtreamSeriesInfoResponse(
    // La información general de la serie, aunque no la uses directamente en el repo, es bueno tenerla.
    val info: XtreamSeries?,
    // Los episodios vienen en un mapa donde la clave es el número de la temporada.
    val episodes: Map<String, List<XtreamEpisode>> = emptyMap(),
    // Las temporadas también suelen venir en un array aparte.
    val seasons: List<Any>? = null // El tipo puede variar, lo dejamos flexible.
)