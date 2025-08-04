package com.andoapps.thundertest.data.remote

import com.andoapps.thundertest.data.model.*
import retrofit2.http.GET
import retrofit2.http.Url

interface XtreamApiService {

    @GET
    suspend fun authenticate(@Url url: String): XtreamResponse

    @GET
    suspend fun getLiveCategories(@Url url: String): List<XtreamCategory>

    @GET
    suspend fun getLiveStreams(@Url url: String): List<XtreamStream>

    @GET
    suspend fun getVodCategories(@Url url: String): List<XtreamCategory>

    @GET
    suspend fun getVodStreams(@Url url: String): List<XtreamVod>

    @GET
    suspend fun getSeriesCategories(@Url url: String): List<XtreamCategory>

    @GET
    suspend fun getSeries(@Url url: String): List<XtreamSeries>

    @GET
    suspend fun getVodInfo(@Url url: String): XtreamVodInfoResponse

    @GET
    suspend fun getSeriesInfo(@Url url: String): XtreamSeriesInfoResponse
}