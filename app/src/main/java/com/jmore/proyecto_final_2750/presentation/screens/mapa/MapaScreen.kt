package com.jmore.proyecto_final_2750.presentation.screens.mapa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmore.proyecto_final_2750.domain.model.Tienda
import com.jmore.proyecto_final_2750.ui.theme.AmarilloAlerta
import com.jmore.proyecto_final_2750.ui.theme.CoralPrimario
import com.jmore.proyecto_final_2750.ui.theme.FondoNoche
import com.jmore.proyecto_final_2750.ui.theme.VerdeAbierto
import com.jmore.proyecto_final_2750.ui.theme.VerdeFondoSuave

@Composable
fun MapaScreen(
    tiendas: List<Tienda>,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    onAbrirIa: () -> Unit,
    onDetalleTienda: (String) -> Unit
) {
    val categorias = listOf("Todos", "Farmacia", "Minimarket", "Comida", "Grifo")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var query by remember { mutableStateOf("") }

    val tiendasFiltradas = remember(tiendas, categoriaSeleccionada, query) {
        tiendas.filter { tienda ->
            val coincideCat = categoriaSeleccionada == "Todos" || tienda.categoria == categoriaSeleccionada
            val q = query.trim().lowercase()
            val coincideTexto = q.isEmpty() ||
                    tienda.nombre.lowercase().contains(q) ||
                    tienda.categoria.lowercase().contains(q) ||
                    tienda.descripcion.lowercase().contains(q)
            coincideCat && coincideTexto
        }
    }

    var tiendaSeleccionadaId by remember { mutableStateOf(tiendas.firstOrNull()?.id) }
    val tiendaDestacada = tiendasFiltradas.find { it.id == tiendaSeleccionadaId }
        ?: tiendasFiltradas.firstOrNull()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = AmarilloAlerta.copy(alpha = 0.16f),
                    border = BorderStroke(1.dp, AmarilloAlerta.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable { onToggleTheme() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Cambiar tema",
                            tint = AmarilloAlerta,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isDarkMode) "Noche" else "Claro",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(VerdeAbierto, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MODO 24/7",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeAbierto
                    )
                }
            }
        }


        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("¿Qué necesitas comprar ahora?", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar")
                        }
                    } else {
                        IconButton(onClick = onAbrirIa) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Nocturna IA",
                                tint = AmarilloAlerta
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }


        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categorias) { cat ->
                    val selected = cat == categoriaSeleccionada
                    FilterChip(
                        selected = selected,
                        onClick = { categoriaSeleccionada = cat },
                        label = {
                            Text(
                                text = cat,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CoralPrimario,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }


        item {
            val mapaBg = if (isDarkMode) Color(0xFF162032) else Color(0xFFEAF0F6)
            val calleColor = if (isDarkMode) Color(0xFF23324A) else Color.White

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = mapaBg),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.18f))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        drawLine(calleColor, Offset(0f, h * 0.35f), Offset(w, h * 0.35f), strokeWidth = 18f)
                        drawLine(calleColor, Offset(0f, h * 0.72f), Offset(w, h * 0.72f), strokeWidth = 14f)
                        drawLine(calleColor, Offset(w * 0.32f, 0f), Offset(w * 0.32f, h), strokeWidth = 16f)
                        drawLine(calleColor, Offset(w * 0.70f, 0f), Offset(w * 0.70f, h), strokeWidth = 14f)
                    }


                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(VerdeAbierto, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${tiendasFiltradas.size} locales abiertos",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Pines interactivos para las tiendas filtradas (hasta 5 visibles en el mapa)
                    val posiciones = listOf(
                        Pair(45.dp, 68.dp),
                        Pair(165.dp, 54.dp),
                        Pair(115.dp, 138.dp),
                        Pair(210.dp, 155.dp),
                        Pair(32.dp, 175.dp)
                    )

                    tiendasFiltradas.take(posiciones.size).forEachIndexed { index, tienda ->
                        val (xPos, yPos) = posiciones[index]
                        val esSeleccionada = tienda.id == tiendaDestacada?.id

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (esSeleccionada) CoralPrimario else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(
                                width = if (esSeleccionada) 2.dp else 1.dp,
                                color = if (esSeleccionada) Color.White else CoralPrimario.copy(alpha = 0.6f)
                            ),
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .offset(x = xPos, y = yPos)
                                .clickable { tiendaSeleccionadaId = tienda.id }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = if (esSeleccionada) Color.White else CoralPrimario,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tienda.nombre.take(14),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (esSeleccionada) Color.White else MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }


        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAbrirIa() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FondoNoche)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = AmarilloAlerta.copy(alpha = 0.18f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = null,
                                tint = AmarilloAlerta,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "¿No encuentras lo que buscas?",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Pregúntale a Nocturna IA qué necesitas",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = AmarilloAlerta
                    )
                }
            }
        }


        if (tiendaDestacada != null) {
            item {
                Text(
                    text = "LOCAL SELECCIONADO EN EL MAPA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetalleTienda(tiendaDestacada.id) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.5.dp, CoralPrimario.copy(alpha = 0.4f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CoralPrimario.copy(alpha = 0.14f),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Storefront,
                                    contentDescription = null,
                                    tint = CoralPrimario,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = VerdeFondoSuave
                                ) {
                                    Text(
                                        text = "• ${tiendaDestacada.estado}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF00695C),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.NearMe,
                                        contentDescription = null,
                                        tint = CoralPrimario,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = tiendaDestacada.distancia,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = tiendaDestacada.nombre,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = tiendaDestacada.direccion,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}