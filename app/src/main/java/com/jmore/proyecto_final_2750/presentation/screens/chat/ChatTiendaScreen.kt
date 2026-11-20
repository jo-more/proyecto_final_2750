package com.jmore.proyecto_final_2750.presentation.screens.chat

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmore.proyecto_final_2750.domain.model.Tienda
import com.jmore.proyecto_final_2750.ui.theme.AmarilloAlerta
import com.jmore.proyecto_final_2750.ui.theme.CoralPrimario
import com.jmore.proyecto_final_2750.ui.theme.VerdeAbierto

private data class MensajeTienda(
    val texto: String,
    val hora: String,
    val esUsuario: Boolean
)

@Composable
fun ChatTiendaScreen(
    tienda: Tienda?,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val nombreTienda = tienda?.nombre ?: "Minimarket El Sol"
    val telefonoTienda = tienda?.telefono ?: "987654321"

    val mensajes = remember(tienda?.id) {
        mutableStateListOf(
            MensajeTienda(
                texto = "¡Hola! ¿Tienen stock de hielo y agua mineral de 2 litros? ¿Atienden por ventanilla?",
                hora = "11:42 PM",
                esUsuario = true
            ),
            MensajeTienda(
                texto = "¡Hola! Sí tenemos ambos en stock. Estamos atendiendo por la ventanilla lateral iluminada. ¿Te los vamos alistando?",
                hora = "11:43 PM",
                esUsuario = false
            )
        )
    }

    var input by remember { mutableStateOf("") }
    val respuestasRapidas = listOf("¿Aceptan Yape?", "Voy en camino (5 min)", "¿Sigue abierto?")

    fun enviarMensaje(texto: String) {
        val limpio = texto.trim()
        if (limpio.isEmpty()) return

        mensajes.add(MensajeTienda(limpio, "Ahora", esUsuario = true))

        val respuestaAuto = when {
            limpio.contains("Yape", ignoreCase = true) ->
                "¡Sí! Aceptamos Yape, Plin y efectivo en ventanilla."
            limpio.contains("camino", ignoreCase = true) ->
                "Perfecto, ya te lo dejamos separado por 20 minutos. ¡Te esperamos!"
            else ->
                "Sí, seguimos atendiendo normal toda la madrugada. ¡Cualquier consulta me avisas!"
        }
        mensajes.add(MensajeTienda(respuestaAuto, "Ahora", esUsuario = false))
        input = ""
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Cabecera exacta a tu diseño
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, end = 12.dp, top = 34.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }


                Box {
                    Surface(
                        shape = CircleShape,
                        color = CoralPrimario.copy(alpha = 0.14f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = CoralPrimario,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(11.dp)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                            .padding(1.5.dp)
                            .background(VerdeAbierto, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = nombreTienda,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = VerdeAbierto,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(VerdeAbierto, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "En línea • Atendiendo ahora",
                            fontSize = 11.sp,
                            color = VerdeAbierto,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }


                Surface(
                    shape = CircleShape,
                    color = AmarilloAlerta.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Tema",
                            tint = AmarilloAlerta,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))


                Surface(
                    shape = CircleShape,
                    color = CoralPrimario.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, CoralPrimario.copy(alpha = 0.35f)),
                    modifier = Modifier.size(36.dp)
                ) {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telefonoTienda"))
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Llamar",
                            tint = CoralPrimario,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f))
        }


        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f))
                    ) {
                        Text(
                            text = "Hoy • Turno de Madrugada 24/7",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            items(mensajes) { msg ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (msg.esUsuario) Alignment.End else Alignment.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (msg.esUsuario) 18.dp else 4.dp,
                            bottomEnd = if (msg.esUsuario) 4.dp else 18.dp
                        ),
                        color = if (msg.esUsuario) CoralPrimario else MaterialTheme.colorScheme.surface,
                        border = if (msg.esUsuario) null else BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.14f)
                        ),
                        modifier = Modifier.fillMaxWidth(0.84f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = msg.texto,
                                color = if (msg.esUsuario) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.hora,
                                fontSize = 10.sp,
                                color = if (msg.esUsuario) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }


        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(respuestasRapidas) { chip ->
                AssistChip(
                    onClick = { enviarMensaje(chip) },
                    label = { Text(chip, fontSize = 12.sp, fontWeight = FontWeight.Medium) }
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Escribe tu consulta sobre stock...", fontSize = 13.sp) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = CircleShape,
                color = CoralPrimario,
                modifier = Modifier.size(46.dp)
            ) {
                IconButton(onClick = { enviarMensaje(input) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}