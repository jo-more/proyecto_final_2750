package com.jmore.proyecto_final_2750.domain.model

data class Tienda(
    val id: String,
    val nombre: String,
    val categoria: String,
    val direccion: String,
    val distancia: String,
    val horario: String,
    val telefono: String,
    val estado: String,
    val atencionVentanilla: Boolean = true,
    val rucVerificado: Boolean = true,
    val descripcion: String
)