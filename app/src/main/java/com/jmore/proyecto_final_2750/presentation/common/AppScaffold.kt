package com.jmore.proyecto_final_2750.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jmore.proyecto_final_2750.presentation.navigation.RutasNav

@Composable
fun AppScaffold(
    navController: NavHostController,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val ruta = navBackStackEntry?.destination?.route

    val mostrarTopBar = ruta?.startsWith("detalle") == true || ruta?.startsWith("form") == true
    val mostrarBottomBar = ruta != RutasNav.BIENVENIDA &&
            ruta != RutasNav.LOGIN &&
            ruta != RutasNav.IA &&
            ruta?.startsWith("chat") != true

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (mostrarTopBar) {
                AppTopBar(
                    navController = navController,
                    isDarkMode = isDarkMode,
                    onToggleTheme = onToggleTheme
                )
            }
        },
        bottomBar = {
            if (mostrarBottomBar) {
                AppBottomBar(navController)
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}