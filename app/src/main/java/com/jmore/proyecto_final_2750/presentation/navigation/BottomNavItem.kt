package com.jmore.proyecto_final_2750.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val ruta: String, val titulo: String, val icono: ImageVector) {
    object Inicio : BottomNavItem(RutasNav.INICIO, "Inicio", Icons.Outlined.Home)
    object Mapa : BottomNavItem(RutasNav.MAPA, "Mapa", Icons.Outlined.Map)
    object IA : BottomNavItem(RutasNav.IA, "IA Asistente", Icons.Outlined.AutoAwesome)
    object MisChats : BottomNavItem(RutasNav.MIS_CHATS, "Mis Chats", Icons.Outlined.ChatBubbleOutline)
    object Perfil : BottomNavItem(RutasNav.PERFIL, "Mi Perfil", Icons.Outlined.Person)

    object Form : BottomNavItem(RutasNav.FORMULARIO, "Partner", Icons.Outlined.Home) {
        fun passId(id: String? = null): String = if (id != null) "form?id=$id" else "form"
    }
    object Detalle : BottomNavItem(RutasNav.DETALLE, "Detalle", Icons.Outlined.Home) {
        fun passId(id: String): String = "detalle/$id"
    }
}