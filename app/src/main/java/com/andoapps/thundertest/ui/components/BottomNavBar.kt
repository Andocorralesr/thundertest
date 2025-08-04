package com.andoapps.thundertest.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andoapps.thundertest.navigation.Screen

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color.Black.copy(alpha = 0.9f)
    ) {
        val items = listOf(
            Screen.Home,
            Screen.Xtream,
            Screen.MyList,
            Screen.Files
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(screen.label, fontSize = 10.sp) },
                icon = {
                    when {
                        screen.iconRes != null -> {
                            Icon(
                                painter = painterResource(id = screen.iconRes),
                                contentDescription = screen.label
                            )
                        }
                        isSelected -> {
                            screen.iconSelected?.let {
                                Icon(it, contentDescription = screen.label)
                            }
                        }
                        else -> {
                            screen.iconUnselected?.let {
                                Icon(it, contentDescription = screen.label)
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
