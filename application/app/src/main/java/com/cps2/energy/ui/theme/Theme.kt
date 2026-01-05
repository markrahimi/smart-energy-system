package com.cps2.energy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme =
        lightColorScheme(
                primary = androidx.compose.ui.graphics.Color(0xFF227093),
                secondary = androidx.compose.ui.graphics.Color(0xFF00897B),
                tertiary = androidx.compose.ui.graphics.Color(0xFFFFA000)
        )

@Composable
fun EnergyTheme(content: @Composable () -> Unit) {
        MaterialTheme(colorScheme = LightColorScheme, typography = Typography, content = content)
}
