package com.jmore.proyecto_final_2750.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.jmore.proyecto_final_2750.domain.model.Tienda
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class TiendaViewModel : ViewModel() {

    private val _tiendas = MutableStateFlow(
        listOf(
            Tienda(
                id = "1",
                nombre = "Inkafarma Express 24H",
                categoria = "Farmacia",
                direccion = "Av. Las Palmeras 1420 (Esq. Grau), Miraflores",
                distancia = "350 m",
                horario = "Abierto 24 horas",
                telefono = "01-314-2020",
                estado = "Abierto ahora",
                atencionVentanilla = true,
                rucVerificado = true,
                descripcion = "Farmacia de turno con atención nocturna por ventanilla segura. Stock verificado de medicamentos de urgencia, sueros y pañales."
            ),
            Tienda(
                id = "2",
                nombre = "Minimarket El Sol 24 Horas",
                categoria = "Minimarket",
                direccion = "Av. José Larco 850, Miraflores",
                distancia = "500 m",
                horario = "Abierto 24 horas",
                telefono = "987-654-321",
                estado = "Abierto ahora",
                atencionVentanilla = true,
                rucVerificado = true,
                descripcion = "Snacks, bebidas, hielo, café americano caliente, cargadores Tipo C y cajero ATM operativo toda la madrugada."
            ),
            Tienda(
                id = "3",
                nombre = "Sanguchería Central 24H",
                categoria = "Comida",
                direccion = "Calle Berlín 412, Miraflores",
                distancia = "720 m",
                horario = "Abierto 24 horas",
                telefono = "912-345-678",
                estado = "Abierto ahora",
                atencionVentanilla = false,
                rucVerificado = true,
                descripcion = "Sánguches calientes, hamburguesas artesanales, café pasado y bebidas calientes para llevar o consumo en barra."
            ),
            Tienda(
                id = "4",
                nombre = "Grifo Primax & Auxilio",
                categoria = "Grifo",
                direccion = "Av. Benavides 1950, Miraflores",
                distancia = "1.1 km",
                horario = "Abierto 24 horas",
                telefono = "01-445-8890",
                estado = "Cierra pronto",
                atencionVentanilla = true,
                rucVerificado = true,
                descripcion = "Combustible 24/7, tienda Listo, inflado de llantas, cables de batería y artículos básicos de emergencia."
            ),
            Tienda(
                id = "5",
                nombre = "Farmacia San Lucas",
                categoria = "Farmacia",
                direccion = "Av. Pardo 620, Miraflores",
                distancia = "1.3 km",
                horario = "Abierto 24 horas",
                telefono = "01-241-9900",
                estado = "Abierto ahora",
                atencionVentanilla = true,
                rucVerificado = true,
                descripcion = "Botica independiente con RUC verificado, atención rápida por ventanilla y consulta de stock por teléfono."
            )
        )
    )
    val tiendas: StateFlow<List<Tienda>> = _tiendas.asStateFlow()

    fun obtenerPorId(id: String?): Tienda? {
        return _tiendas.value.find { it.id == id }
    }

    fun agregarTienda(
        nombre: String,
        categoria: String,
        direccion: String,
        horario: String,
        telefono: String,
        estado: String,
        descripcion: String
    ) {
        val nueva = Tienda(
            id = UUID.randomUUID().toString().take(8),
            nombre = nombre.trim(),
            categoria = categoria,
            direccion = direccion.trim(),
            distancia = "400 m",
            horario = horario.trim().ifEmpty { "Abierto 24 horas" },
            telefono = telefono.trim(),
            estado = estado,
            atencionVentanilla = true,
            rucVerificado = true,
            descripcion = descripcion.trim()
        )
        _tiendas.update { listOf(nueva) + it }
    }

    fun actualizarTienda(tiendaActualizada: Tienda) {
        _tiendas.update { lista ->
            lista.map { if (it.id == tiendaActualizada.id) tiendaActualizada else it }
        }
    }

    fun eliminarTienda(id: String) {
        _tiendas.update { lista -> lista.filterNot { it.id == id } }
    }
}