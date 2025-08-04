// ruta: com/andoapps/thundertest/ui/screens/xtream/ProfileUiState.kt
package com.andoapps.thundertest.ui.screens.xtream

import com.andoapps.thundertest.data.model.XtreamCategory
import com.andoapps.thundertest.data.model.XtreamContentItem

data class ProfileUiState(
    val isLoading: Boolean = true,
    val profileName: String = "",
    val allContent: List<XtreamContentItem> = emptyList(),
    val liveCategories: List<XtreamCategory> = emptyList(),
    val vodCategories: List<XtreamCategory> = emptyList(),
    val seriesCategories: List<XtreamCategory> = emptyList(),
    val error: String? = null
)