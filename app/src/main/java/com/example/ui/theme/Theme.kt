package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EngineeringLabColorScheme = darkColorScheme(
    primary = IndustrialAmber,
    onPrimary = Graphite950,
    primaryContainer = IndustrialAmberDeep,
    onPrimaryContainer = IndustrialAmberBright,
    secondary = Ivory100,
    onSecondary = Graphite950,
    secondaryContainer = Graphite850,
    onSecondaryContainer = Ivory200,
    tertiary = MetallicSilver,
    onTertiary = Graphite950,
    tertiaryContainer = Graphite800,
    onTertiaryContainer = Ivory100,
    background = Graphite950,
    onBackground = Ivory100,
    surface = Graphite900,
    onSurface = Ivory100,
    surfaceVariant = Graphite850,
    onSurfaceVariant = MetallicSilver,
    outline = Graphite700,
    outlineVariant = NeutralDivider,
    error = LabFaultWarning,
    onError = Ivory50
)

@Composable
fun IndusNexusTheme(
    content: @Composable () -> Unit
) {
    // Laboratory control center aesthetic uses dedicated industrial graphite & amber theme
    MaterialTheme(
        colorScheme = EngineeringLabColorScheme,
        typography = Typography,
        content = content
    )
}
