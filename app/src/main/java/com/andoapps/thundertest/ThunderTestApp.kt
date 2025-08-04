package com.andoapps.thundertest

import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andoapps.thundertest.navigation.AppNavigation
import com.andoapps.thundertest.navigation.Screen
import com.andoapps.thundertest.ui.components.AppTopBar
import com.andoapps.thundertest.ui.components.BottomNavBar

@Composable
fun ThunderTestApp() {
    // El fondo dinámico se mantiene igual
    val dominantColor by remember { mutableStateOf(Color.Black) }
    // ... (el código de LaunchedEffect para extraer el color dominante se mantiene igual)

    val backgroundBrush = remember(dominantColor) {
        Brush.verticalGradient(
            colors = listOf(dominantColor, Color.Black),
            startY = 0f,
            endY = 1500f
        )
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Los estados para el título y las acciones se mantienen aquí
    var topBarTitle by remember { mutableStateOf("") }
    var topBarActions: @Composable (() -> Unit) by remember { mutableStateOf({}) }

    // --- CAMBIO CLAVE AQUÍ ---
    // La lógica ahora comprueba si la ruta actual es la de ProfileContent.
    val showBackButton = currentRoute == Screen.ProfileContent.route

    Scaffold(
        topBar = {
            AppTopBar(
                title = topBarTitle,
                showBackButton = showBackButton,
                onNavigateUp = { navController.navigateUp() },
                actions = { topBarActions() }
            )
        },
        containerColor = Color.Transparent,
        bottomBar = { BottomNavBar(navController = navController) },
        modifier = Modifier.background(backgroundBrush)
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            paddingValues = innerPadding,
            setTopBar = { title, actions ->
                topBarTitle = title
                topBarActions = actions
            }
        )
    }
}