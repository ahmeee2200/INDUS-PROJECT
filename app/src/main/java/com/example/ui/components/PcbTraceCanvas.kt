package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.Graphite800
import com.example.ui.theme.IndustrialAmber
import com.example.ui.theme.IndustrialAmberBright

/**
 * High-precision laboratory PCB circuit traces.
 * Draws matte circuit board bus lines and subtle, restrained amber electron pulses.
 */
@Composable
fun PcbTraceCanvas(
    modifier: Modifier = Modifier,
    traceColor: Color = Graphite800,
    pulseColor: Color = IndustrialAmber
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pcbPulse")
    val pulseProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseProgress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Trace 1: Top Left to Right Bus
        val path1 = Path().apply {
            moveTo(w * 0.05f, h * 0.15f)
            lineTo(w * 0.35f, h * 0.15f)
            lineTo(w * 0.45f, h * 0.25f)
            lineTo(w * 0.85f, h * 0.25f)
            lineTo(w * 0.92f, h * 0.35f)
        }

        // Trace 2: Bottom Left angle
        val path2 = Path().apply {
            moveTo(w * 0.12f, h * 0.85f)
            lineTo(w * 0.30f, h * 0.85f)
            lineTo(w * 0.42f, h * 0.72f)
            lineTo(w * 0.70f, h * 0.72f)
            lineTo(w * 0.80f, h * 0.60f)
            lineTo(w * 0.95f, h * 0.60f)
        }

        // Trace 3: Vertical telemetry bus
        val path3 = Path().apply {
            moveTo(w * 0.88f, h * 0.10f)
            lineTo(w * 0.88f, h * 0.45f)
            lineTo(w * 0.82f, h * 0.52f)
            lineTo(w * 0.82f, h * 0.90f)
        }

        // Draw base traces
        val traceStroke = Stroke(width = 1.5f, cap = StrokeCap.Round)
        drawPath(path = path1, color = traceColor, style = traceStroke)
        drawPath(path = path2, color = traceColor, style = traceStroke)
        drawPath(path = path3, color = traceColor, style = traceStroke)

        // Draw PCB Vias (solder pads)
        val padColor = traceColor
        drawCircle(color = padColor, radius = 4f, center = Offset(w * 0.05f, h * 0.15f))
        drawCircle(color = padColor, radius = 4f, center = Offset(w * 0.92f, h * 0.35f))
        drawCircle(color = padColor, radius = 4f, center = Offset(w * 0.12f, h * 0.85f))
        drawCircle(color = padColor, radius = 4f, center = Offset(w * 0.95f, h * 0.60f))
        drawCircle(color = padColor, radius = 4f, center = Offset(w * 0.88f, h * 0.10f))
        drawCircle(color = padColor, radius = 4f, center = Offset(w * 0.82f, h * 0.90f))

        // Restrained subtle pulse traveling along Trace 1
        val p1x = when {
            pulseProgress < 0.3f -> w * 0.05f + (w * 0.30f) * (pulseProgress / 0.3f)
            pulseProgress < 0.5f -> w * 0.35f + (w * 0.10f) * ((pulseProgress - 0.3f) / 0.2f)
            else -> w * 0.45f + (w * 0.40f) * ((pulseProgress - 0.5f) / 0.5f)
        }
        val p1y = when {
            pulseProgress < 0.3f -> h * 0.15f
            pulseProgress < 0.5f -> h * 0.15f + (h * 0.10f) * ((pulseProgress - 0.3f) / 0.2f)
            else -> h * 0.25f
        }

        drawCircle(
            color = pulseColor.copy(alpha = 0.85f),
            radius = 3.5f,
            center = Offset(p1x, p1y)
        )
        drawCircle(
            color = IndustrialAmberBright.copy(alpha = 0.25f),
            radius = 7f,
            center = Offset(p1x, p1y)
        )
    }
}
