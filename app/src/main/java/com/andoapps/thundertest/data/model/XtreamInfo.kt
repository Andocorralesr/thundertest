// ruta: com/andoapps/thundertest/data/model/XtreamInfo.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamResponse(
    @param:Json(name = "user_info")   val userInfo: UserInfo,
    @param:Json(name = "server_info") val serverInfo: ServerInfo
)

@JsonClass(generateAdapter = true)
data class UserInfo(
    val username: String,
    val password: String,
    val message: String,
    val auth: Int,
    val status: String,
    @param:Json(name = "exp_date")               val expDate: String?,
    @param:Json(name = "is_trial")               val isTrial: String,
    @param:Json(name = "active_cons")            val activeConnections: String,
    @param:Json(name = "created_at")             val createdAt: String,
    @param:Json(name = "max_connections")        val maxConnections: String,
    @param:Json(name = "allowed_output_formats") val allowedOutputFormats: List<String>
)

@JsonClass(generateAdapter = true)
data class ServerInfo(
    val url: String,
    val port: String,
    @param:Json(name = "https_port")      val httpsPort: String,
    @param:Json(name = "server_protocol") val serverProtocol: String,
    @param:Json(name = "rtmp_port")       val rtmpPort: String,
    val timezone: String,
    @param:Json(name = "timestamp_now")   val timestampNow: Long,
    @param:Json(name = "time_now")        val timeNow: String
)