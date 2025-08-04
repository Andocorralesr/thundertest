package com.andoapps.thundertest.data.model

data class XtreamEpgResponse(
    val epg_listings: List<XtreamEpgProgram>?
)

data class XtreamEpgProgram(
    val id: Int,
    val epg_id: String,
    val title: String,
    val start: String,        // Formato: "2025-08-04 15:00:00"
    val end: String,
    val description: String?,
    val has_archive: Int?,    // 1 si tiene catch-up, 0 o null si no
    val now_playing: Int?     // 1 si es el programa actual, opcional
)
