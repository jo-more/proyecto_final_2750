package com.jmore.proyecto_final_2750.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CoralPrimario,
    secondary = VerdeAbierto,
    tertiary = AmarilloAlerta,
    background = FondoNoche,
    surface = SuperficieNoche,
    onPrimary = Color.White,
    onBackground = TextoClaroNoche,
    onSurface = TextoClaroNoche,
    onSurfaceVariant = TextoSecundarioNoche
)

private val LightColorScheme = lightColorScheme(
    primary = CoralPrimario,
    secondary = VerdeAbierto,
    tertiary = AmarilloAlerta,
    background = FondoDia,
    surface = SuperficieDia,
    onPrimary = Color.White,
    onBackground = TextoOscuroDia,
    onSurface = TextoOscuroDia,
    onSurfaceVariant = TextoSecundarioDia
)

@Composable
fun Proyecto_final_2750Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}