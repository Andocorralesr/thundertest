// ruta: com/andoapps/thundertest/data/model/XtreamCategory.kt
package com.andoapps.thundertest.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class XtreamCategory(
    @param:Json(name = "category_id")   val categoryId: String,
    @param:Json(name = "category_name") val categoryName: String
)