// ruta: com/andoapps/thundertest/data/repository/XtreamRepository.kt
package com.andoapps.thundertest.data.repository

import com.andoapps.thundertest.data.model.*
import com.andoapps.thundertest.data.remote.XtreamApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XtreamRepository @Inject constructor(
    private val apiService: XtreamApiService
) {
    // El sistema de caché en memoria se mantiene, es una buena práctica.
    private val liveCatCache = mutableMapOf<String, List<XtreamCategory>>()
    private val liveStreamCache = mutableMapOf<String, List<M3UChannel>>()
    private val vodCatCache  = mutableMapOf<String, List<XtreamCategory>>()
    private val vodStreamCache = mutableMapOf<String, List<XtreamVod>>()
    private val seriesCatCache = mutableMapOf<String, List<XtreamCategory>>()
    private val seriesCache = mutableMapOf<String, List<XtreamSeries>>()
    private val infoCache   = mutableMapOf<String, XtreamResponse>()

    private fun normalize(base: String): String =
        base.trim().removeSuffix("/").let {
            if (!it.startsWith("http")) "http://$it" else it
        }

    suspend fun getXtreamInfo(base: String, user: String, pass: String): XtreamResponse {
        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass"
        return infoCache[url] ?: apiService.authenticate(url).also { infoCache[url] = it }
    }

    suspend fun getLiveCategories(base: String, user: String, pass: String): List<XtreamCategory> {
        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_live_categories"
        return liveCatCache[url] ?: apiService.getLiveCategories(url).also { liveCatCache[url] = it }
    }

    suspend fun getLiveStreams(base: String, user: String, pass: String): List<M3UChannel> {
        val cacheKey = "live_streams_${normalize(base)}_$user"
        liveStreamCache[cacheKey]?.let { return it }

        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_live_streams"
        return apiService.getLiveStreams(url).mapNotNull { s ->
            if (!s.name.isNullOrBlank() && s.categoryId != null && s.streamId != null) {
                M3UChannel(
                    id    = s.streamId.toString(), // <-- ASIGNAR EL ID AQUÍ
                    name  = s.name,
                    url   = "${normalize(base)}/live/$user/$pass/${s.streamId}.m3u8",
                    group = s.categoryId,
                    logo  = s.streamIcon
                )
            } else null
        }.also { liveStreamCache[cacheKey] = it }
    }

    suspend fun getVodCategories(base: String, user: String, pass: String): List<XtreamCategory> {
        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_vod_categories"
        return vodCatCache[url] ?: apiService.getVodCategories(url).also { vodCatCache[url] = it }
    }

    suspend fun getVodStreams(base: String, user: String, pass: String): List<XtreamVod> {
        val cacheKey = "vod_streams_${normalize(base)}_$user"
        vodStreamCache[cacheKey]?.let { return it }

        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_vod_streams"
        return apiService.getVodStreams(url).also { vodStreamCache[cacheKey] = it }
    }

    suspend fun getSeriesCategories(base: String, user: String, pass: String): List<XtreamCategory> {
        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_series_categories"
        return seriesCatCache[url] ?: apiService.getSeriesCategories(url).also { seriesCatCache[url] = it }
    }

    suspend fun getSeries(base: String, user: String, pass: String): List<XtreamSeries> {
        val cacheKey = "series_${normalize(base)}_$user"
        seriesCache[cacheKey]?.let { return it }

        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_series"
        return apiService.getSeries(url).also { seriesCache[cacheKey] = it }
    }

    suspend fun getVodDetails(base: String, user: String, pass: String, vodId: Int): XtreamVod? {
        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_vod_info&vod_id=$vodId"
        val response = apiService.getVodInfo(url)

        return response.movieData?.copy(
            plot = response.info?.plot,
            rating = response.info?.rating?.toDoubleOrNull(),
            releaseDate = response.info?.releaseDate,
            duration = response.info?.duration,
            streamIcon = response.info?.movieImage ?: response.movieData.streamIcon
        )
    }

    suspend fun getEpisodes(base: String, user: String, pass: String, seriesId: Int): List<XtreamEpisode> {
        val url = "${normalize(base)}/player_api.php?username=$user&password=$pass&action=get_series_info&series_id=$seriesId"
        val response = apiService.getSeriesInfo(url)

        return response.episodes.flatMap { (seasonNumber, episodeList) ->
            episodeList.onEach { it.season = seasonNumber }
        }
    }
}