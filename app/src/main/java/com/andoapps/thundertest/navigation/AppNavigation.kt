package com.andoapps.thundertest.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.andoapps.thundertest.ui.screens.FilesScreen
import com.andoapps.thundertest.ui.screens.HomeScreen
import com.andoapps.thundertest.ui.screens.MyListScreen
import com.andoapps.thundertest.ui.screens.xtream.ProfileScreen
import com.andoapps.thundertest.ui.screens.XtreamScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    setTopBar: (String, @Composable () -> Unit) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            LaunchedEffect(Unit) {
                setTopBar("Inicio") {
                    IconButton(onClick = { /* TODO: Lógica de búsqueda */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            }
            HomeScreen(paddingValues = paddingValues)
        }

        navigation(
            route = Screen.Xtream.route,
            startDestination = "profile_selection"
        ) {
            composable(route = "profile_selection") {
                LaunchedEffect(Unit) {
                    setTopBar("Perfiles Xtream") {
                        IconButton(onClick = { /* TODO: Mostrar info */ }) {
                            Icon(Icons.Default.Info, contentDescription = "Información")
                        }
                    }
                }
                XtreamScreen(
                    paddingValues = paddingValues,
                    onProfileClick = { profile ->
                        navController.navigate(Screen.ProfileContent.withArgs(profile.id))
                    }
                )
            }

            // --- ACTUALIZACIÓN AQUÍ ---
            composable(
                route = Screen.ProfileContent.route,
                arguments = listOf(navArgument("profileId") { type = NavType.StringType })
            ) {
                // Ya no extraemos 'profileId' ni usamos LaunchedEffect aquí.
                // La pantalla ahora es autosuficiente gracias a su ViewModel.
                ProfileScreen(
                    paddingValues = paddingValues,
                    setTopBar = setTopBar
                )
            }
        }

        composable(Screen.MyList.route) {
            LaunchedEffect(Unit) {
                setTopBar("Mis Cosas") {}
            }
            MyListScreen(paddingValues = paddingValues)
        }

        composable(Screen.Files.route) {
            LaunchedEffect(Unit) {
                setTopBar("Archivos Locales") {}
            }
            FilesScreen(paddingValues = paddingValues)
        }
    }
}
