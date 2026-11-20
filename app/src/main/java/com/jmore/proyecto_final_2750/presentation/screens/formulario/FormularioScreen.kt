package com.jmore.proyecto_final_2750.presentation.screens.formulario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmore.proyecto_final_2750.domain.model.Tienda
import com.jmore.proyecto_final_2750.ui.theme.CoralPrimario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    tiendaEditar: Tienda? = null,
    onGuardar: (
        nombre: String,
        categoria: String,
        direccion: String,
        horario: String,
        telefono: String,
        estado: String,
        descripcion: String
    ) -> Unit
) {
    val categorias = listOf("Farmacia", "Minimarket", "Comida", "Grifo")
    val estados = listOf("Abierto ahora", "Cierra pronto", "Cerrado")

    var nombre by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.nombre ?: "") }
    var categoria by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.categoria ?: "Farmacia") }
    var direccion by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.direccion ?: "") }
    var horario by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.horario ?: "Abierto 24 horas") }
    var telefono by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.telefono ?: "") }
    var estado by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.estado ?: "Abierto ahora") }
    var descripcion by remember(tiendaEditar) { mutableStateOf(tiendaEditar?.descripcion ?: "") }

    var expandedCat by remember { mutableStateOf(false) }
    var expandedEstado by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (tiendaEditar == null) "Registrar Negocio 24/7" else "Editar Negocio",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; errorMsg = null },
            label = { Text("Nombre del local *") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )


        ExposedDropdownMenuBox(
            expanded = expandedCat,
            onExpandedChange = { expandedCat = !expandedCat },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = categoria,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat) },
                modifier = Modifier
                    .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedCat,
                onDismissRequest = { expandedCat = false }
            ) {
                categorias.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            categoria = opcion
                            expandedCat = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it; errorMsg = null },
            label = { Text("Dirección exacta *") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = horario,
            onValueChange = { horario = it },
            label = { Text("Horario de atención") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono / WhatsApp") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )


        ExposedDropdownMenuBox(
            expanded = expandedEstado,
            onExpandedChange = { expandedEstado = !expandedEstado },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = estado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Estado actual") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstado) },
                modifier = Modifier
                    .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedEstado,
                onDismissRequest = { expandedEstado = false }
            ) {
                estados.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            estado = opcion
                            expandedEstado = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Productos o servicios disponibles") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMsg != null) {
            Text(
                text = errorMsg!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                if (nombre.isBlank() || direccion.isBlank()) {
                    errorMsg = "Completa al menos el nombre y la dirección del local."
                } else {
                    onGuardar(nombre, categoria, direccion, horario, telefono, estado, descripcion)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = CoralPrimario),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = if (tiendaEditar == null) "Guardar Local" else "Actualizar Local",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}