package com.jmore.proyecto_final_2750.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jmore.proyecto_final_2750.presentation.navigation.BottomNavItem
import com.jmore.proyecto_final_2750.ui.theme.AmarilloAlerta
import com.jmore.proyecto_final_2750.ui.theme.CoralPrimario
import com.jmore.proyecto_final_2750.ui.theme.FondoNoche

@Composable
fun AppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val itemsIzquierda = listOf(BottomNavItem.Inicio, BottomNavItem.Mapa)
    val itemsDerecha = listOf(BottomNavItem.MisChats, BottomNavItem.Perfil)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.15f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                itemsIzquierda.forEach { item ->
                    BottomBarItemView(
                        item = item,
                        selected = currentRoute == item.ruta,
                        showBadge = false,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(item.ruta) { launchSingleTop = true }
                        }
                    )
                }


                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate(BottomNavItem.IA.ruta) { launchSingleTop = true }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.height(34.dp))
                    Text(
                        text = "IA Asistente",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                itemsDerecha.forEach { item ->
                    BottomBarItemView(
                        item = item,
                        selected = currentRoute == item.ruta,
                        showBadge = item is BottomNavItem.MisChats,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(item.ruta) { launchSingleTop = true }
                        }
                    )
                }
            }
        }


        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-18).dp)
                .size(56.dp)
                .clickable {
                    navController.navigate(BottomNavItem.IA.ruta) { launchSingleTop = true }
                },
            shape = RoundedCornerShape(18.dp),
            color = FondoNoche,
            border = BorderStroke(2.dp, AmarilloAlerta.copy(alpha = 0.7f)),
            shadowElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = BottomNavItem.IA.icono,
                    contentDescription = "IA Asistente",
                    tint = AmarilloAlerta,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun BottomBarItemView(
    item: BottomNavItem,
    selected: Boolean,
    showBadge: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val color = if (selected) CoralPrimario else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Icon(
                imageVector = item.icono,
                contentDescription = item.titulo,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            if (showBadge) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-2).dp)
                        .size(8.dp)
                        .background(CoralPrimario, CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.titulo,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = color
        )
    }
}