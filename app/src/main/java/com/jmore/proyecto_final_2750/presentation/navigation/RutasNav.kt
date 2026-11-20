package com.jmore.proyecto_final_2750.presentation.navigation

object RutasNav {
    const val BIENVENIDA = "bienvenida"
    const val LOGIN = "login"
    const val INICIO = "inicio"
    const val MAPA = "mapa"
    const val IA = "ia"
    const val MIS_CHATS = "mis_chats"
    const val CHAT_TIENDA = "chat/{id}"
    const val PERFIL = "perfil"
    const val FORMULARIO = "form?id={id}"
    const val DETALLE = "detalle/{id}"

    fun chatTienda(id: String): String = "chat/$id"

    fun getTitulo(ruta: String?): String {
        return when {
            ruta == INICIO -> "Turno 24"
            ruta == MAPA -> "Mapa 24/7"
            ruta == MIS_CHATS -> "Mis Chats"
            ruta == PERFIL -> "Mi Perfil"
            ruta?.startsWith("form") == true -> "Panel Partner"
            ruta?.startsWith("detalle") == true -> "Detalle del Local"
            else -> "Turno 24"
        }
    }
}