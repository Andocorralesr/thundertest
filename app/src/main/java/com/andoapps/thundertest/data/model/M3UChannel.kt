// ruta: com/andoapps/thundertest/data/model/M3UChannel.kt
package com.andoapps.thundertest.data.model

data class M3UChannel(
    val id: String,
    val name: String,
    val url: String,
    val group: String? = null,
    val logo: String? = null
)