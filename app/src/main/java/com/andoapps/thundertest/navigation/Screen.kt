package com.andoapps.thundertest.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.andoapps.thundertest.R

sealed class Screen(
    val route: String,
    val label: String,
    val iconSelected: ImageVector? = null,
    val iconUnselected: ImageVector? = null,
    @param:DrawableRes val iconRes: Int? = null
) {
    data object Home : Screen(
        route = "home",
        label = "Inicio",
        iconSelected = Icons.Filled.Home,
        iconUnselected = Icons.Outlined.Home
    )

    data object Xtream : Screen(
        route = "xtream",
        label = "Xtream",
        iconRes = R.drawable.ic_iptv
    )

    data object MyList : Screen(
        route = "my_list",
        label = "Mi lista",
        iconSelected = Icons.Filled.Bookmark,
        iconUnselected = Icons.Outlined.BookmarkBorder
    )

    data object Files : Screen(
        route = "files",
        label = "Archivos",
        iconSelected = Icons.Filled.Folder,
        iconUnselected = Icons.Outlined.Folder
    )

    // --- Pantalla anidada para el contenido del perfil ---
    // No necesita su propia etiqueta o íconos, ya que vive dentro del flujo de "Xtream"
    data object ProfileContent : Screen(
        route = "profile_content/{profileId}",
        label = "Contenido del Perfil" // Esta etiqueta ya no es crítica, pero la mantenemos por claridad
    ) {
        fun withArgs(profileId: String): String {
            return "profile_content/$profileId"
        }
    }
}