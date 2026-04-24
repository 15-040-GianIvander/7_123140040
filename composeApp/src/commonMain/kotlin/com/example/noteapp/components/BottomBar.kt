package com.example.noteapp.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.KeepPrimary
import com.example.noteapp.navigation.Screen

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.Notes,
        Screen.Favorites,
        Screen.Profile,
        Screen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                icon = {
                    val iconText = when (screen) {
                        Screen.Notes -> "🗒"
                        Screen.Favorites -> if (isSelected) "❤️" else "🤍" // Diubah menjadi love
                        Screen.Profile -> "👤"
                        Screen.Settings -> "⚙"
                        else -> "•"
                    }
                    Text(text = iconText, fontSize = 22.sp)
                },
                onClick = {
                    navController.navigate(screen.route) {
                        val startRoute = navController.graph.startDestinationRoute
                        if (startRoute != null) {
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = KeepPrimary,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = KeepPrimary.copy(alpha = 0.1f)
                )
            )
        }
    }
}
