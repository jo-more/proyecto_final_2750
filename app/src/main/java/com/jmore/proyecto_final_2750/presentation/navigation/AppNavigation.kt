package com.jmore.proyecto_final_2750.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jmore.proyecto_final_2750.presentation.screens.bienvenida.BienvenidaScreen
import com.jmore.proyecto_final_2750.presentation.screens.chat.ChatTiendaScreen
import com.jmore.proyecto_final_2750.presentation.screens.chat.MisChatsScreen
import com.jmore.proyecto_final_2750.presentation.screens.detalle.DetalleScreen
import com.jmore.proyecto_final_2750.presentation.screens.formulario.FormularioScreen
import com.jmore.proyecto_final_2750.presentation.screens.ia.NocturnaIaScreen
import com.jmore.proyecto_final_2750.presentation.screens.inicio.InicioScreen
import com.jmore.proyecto_final_2750.presentation.screens.login.LoginScreen
import com.jmore.proyecto_final_2750.presentation.screens.mapa.MapaScreen
import com.jmore.proyecto_final_2750.presentation.screens.perfil.PerfilScreen
import com.jmore.proyecto_final_2750.presentation.viewmodel.TiendaViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    tiendaViewModel: TiendaViewModel = viewModel()
) {
    val tiendas by tiendaViewModel.tiendas.collectAsState()

    NavHost(
        navController = navController,
        startDestination = RutasNav.BIENVENIDA,
        modifier = Modifier
    )

    {
        composable(BottomNavItem.MisChats.ruta) {
            MisChatsScreen(
                tiendas = tiendas,
                onAbrirChat = { tiendaId ->
                    navController.navigate(RutasNav.chatTienda(tiendaId))
                }
            )
        }

        composable(RutasNav.CHAT_TIENDA) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val tienda = tiendas.find { it.id == id }
            ChatTiendaScreen(
                tienda = tienda,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                onBack = { navController.popBackStack() }
            )
        }

        composable(BottomNavItem.Perfil.ruta) {
            PerfilScreen(
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                onIrPanelPartner = { navController.navigate(BottomNavItem.Form.passId(null)) },
                onCerrarSesion = {
                    navController.navigate(RutasNav.BIENVENIDA) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }



        composable(RutasNav.BIENVENIDA) {
            BienvenidaScreen(
                onClienteClick = { navController.navigate(RutasNav.LOGIN) },
                onPartnerClick = { navController.navigate(BottomNavItem.Form.passId(null)) }
            )
        }

        composable(RutasNav.LOGIN) {
            LoginScreen(
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                onBack = { navController.popBackStack() },
                onIngresar = {
                    navController.navigate(RutasNav.INICIO) {
                        popUpTo(RutasNav.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(BottomNavItem.Inicio.ruta) {
            InicioScreen(
                tiendas = tiendas,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                onAbrirMapa = { navController.navigate(BottomNavItem.Mapa.ruta) },
                onAbrirIa = { navController.navigate(BottomNavItem.IA.ruta) },
                onDetalleTienda = { id ->
                    navController.navigate(BottomNavItem.Detalle.passId(id))
                }
            )
        }

        composable(BottomNavItem.Mapa.ruta) {
            MapaScreen(
                tiendas = tiendas,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                onAbrirIa = { navController.navigate(BottomNavItem.IA.ruta) },
                onDetalleTienda = { id ->
                    navController.navigate(BottomNavItem.Detalle.passId(id))
                }
            )
        }

        composable(BottomNavItem.IA.ruta) {
            NocturnaIaScreen(
                tiendas = tiendas,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme,
                onBack = { navController.popBackStack() },
                onDetalleTienda = { id ->
                    navController.navigate(BottomNavItem.Detalle.passId(id))
                }
            )
        }

        composable(route = BottomNavItem.Detalle.ruta) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val tienda = tiendas.find { it.id == id }

            DetalleScreen(
                tienda = tienda,
                onEditar = { tiendaId ->
                    navController.navigate(BottomNavItem.Form.passId(tiendaId))
                },
                onEliminar = { tiendaId ->
                    tiendaViewModel.eliminarTienda(tiendaId)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = BottomNavItem.Form.ruta,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val tiendaEditar = tiendas.find { it.id == id }

            FormularioScreen(
                tiendaEditar = tiendaEditar,
                onGuardar = { nombre, categoria, direccion, horario, telefono, estado, descripcion ->
                    if (tiendaEditar == null) {
                        tiendaViewModel.agregarTienda(
                            nombre = nombre,
                            categoria = categoria,
                            direccion = direccion,
                            horario = horario,
                            telefono = telefono,
                            estado = estado,
                            descripcion = descripcion
                        )
                    } else {
                        tiendaViewModel.actualizarTienda(
                            tiendaEditar.copy(
                                nombre = nombre,
                                categoria = categoria,
                                direccion = direccion,
                                horario = horario,
                                telefono = telefono,
                                estado = estado,
                                descripcion = descripcion
                            )
                        )
                    }
                    navController.navigate(BottomNavItem.Inicio.ruta) {
                        popUpTo(BottomNavItem.Inicio.ruta) { inclusive = true }
                    }
                }
            )
        }
    }
}