package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BrushedAluminum
import com.example.ui.theme.Graphite700
import com.example.ui.theme.Graphite800
import com.example.ui.theme.Graphite900
import com.example.ui.theme.Graphite950
import com.example.ui.theme.IndustrialAmber
import com.example.ui.theme.IndustrialAmberBright
import com.example.ui.theme.Ivory200
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark

/**
 * High-fidelity 3D-rendered Microcontroller Module.
 * Features realistic matte black silicon body, brushed metal heat shield,
 * surface-mounted pin headers, and subtle restrained status pulse.
 */
@Composable
fun Microcontroller3DCanvas(
    modifier: Modifier = Modifier,
    label: String = "STM32H743ZI"
) {
    var manualTiltX by remember { mutableFloatStateOf(0f) }
    var manualTiltY by remember { mutableFloatStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "mcuAnim")
    val subtleOscillation by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "subtleTilt"
    )

    val ledPulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ledPulse"
    )

    Box(
        modifier = modifier
            .size(170.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        manualTiltX = (manualTiltX - dragAmount.y * 0.15f).coerceIn(-12f, 12f)
                        manualTiltY = (manualTiltY + dragAmount.x * 0.15f).coerceIn(-12f, 12f)
                    },
                    onDragEnd = {
                        manualTiltX = 0f
                        manualTiltY = 0f
                    }
                )
            }
            .graphicsLayer {
                rotationX = 14f + subtleOscillation + manualTiltX
                rotationY = -12f + (subtleOscillation * 0.7f) + manualTiltY
                cameraDistance = 16f * density
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f
            val chipSide = w * 0.72f
            val halfSide = chipSide / 2f

            // 1. Soft Realistic Drop Shadow under the IC
            drawRoundRect(
                color = Color.Black.copy(alpha = 0.55f),
                topLeft = Offset(cx - halfSide + 4f, cy - halfSide + 8f),
                size = Size(chipSide, chipSide),
                cornerRadius = CornerRadius(10f, 10f)
            )

            // 2. Peripheral Pins (4 sides) - metallic silver & warm gold reflections
            val pinCount = 8
            val pinSpacing = chipSide / (pinCount + 1)
            val pinLength = 8f
            val pinWidth = 3f

            for (i in 1..pinCount) {
                val offsetPos = (cx - halfSide) + i * pinSpacing
                // Top Pins
                drawRect(
                    color = MetallicSilver,
                    topLeft = Offset(offsetPos - pinWidth / 2f, cy - halfSide - pinLength),
                    size = Size(pinWidth, pinLength)
                )
                // Bottom Pins
                drawRect(
                    color = MetallicSilverDark,
                    topLeft = Offset(offsetPos - pinWidth / 2f, cy + halfSide),
                    size = Size(pinWidth, pinLength)
                )
                // Left Pins
                drawRect(
                    color = MetallicSilver,
                    topLeft = Offset(cx - halfSide - pinLength, offsetPos - pinWidth / 2f),
                    size = Size(pinLength, pinWidth)
                )
                // Right Pins
                drawRect(
                    color = MetallicSilverDark,
                    topLeft = Offset(cx + halfSide, offsetPos - pinWidth / 2f),
                    size = Size(pinLength, pinWidth)
                )
            }

            // 3. Matte Black IC Package Body
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(Graphite800, Graphite900, Graphite950),
                    start = Offset(cx - halfSide, cy - halfSide),
                    end = Offset(cx + halfSide, cy + halfSide)
                ),
                topLeft = Offset(cx - halfSide, cy - halfSide),
                size = Size(chipSide, chipSide),
                cornerRadius = CornerRadius(8f, 8f)
            )

            // Package Bevel Border
            drawRoundRect(
                color = Graphite700,
                topLeft = Offset(cx - halfSide, cy - halfSide),
                size = Size(chipSide, chipSide),
                cornerRadius = CornerRadius(8f, 8f),
                style = Stroke(width = 1.2f)
            )

            // 4. Brushed Metal Heatspreader Core (Center square)
            val metalSide = chipSide * 0.58f
            val halfMetal = metalSide / 2f
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(BrushedAluminum, Graphite800, BrushedAluminum),
                    start = Offset(cx - halfMetal, cy - halfMetal),
                    end = Offset(cx + halfMetal, cy + halfMetal)
                ),
                topLeft = Offset(cx - halfMetal, cy - halfMetal),
                size = Size(metalSide, metalSide),
                cornerRadius = CornerRadius(5f, 5f)
            )

            // Brushed Metal Inset Border
            drawRoundRect(
                color = MetallicSilverDark.copy(alpha = 0.5f),
                topLeft = Offset(cx - halfMetal, cy - halfMetal),
                size = Size(metalSide, metalSide),
                cornerRadius = CornerRadius(5f, 5f),
                style = Stroke(width = 1f)
            )

            // Pin 1 Index Dot (Warm Amber / Gold Notch)
            drawCircle(
                color = IndustrialAmber,
                radius = 3f,
                center = Offset(cx - halfSide + 12f, cy - halfSide + 12f)
            )

            // 5. Status Telemetry LED (Subtle Industrial Amber)
            val ledX = cx + halfSide - 14f
            val ledY = cy - halfSide + 14f
            drawCircle(
                color = IndustrialAmber.copy(alpha = ledPulse),
                radius = 3.5f,
                center = Offset(ledX, ledY)
            )
            drawCircle(
                color = IndustrialAmberBright.copy(alpha = ledPulse * 0.35f),
                radius = 7.5f,
                center = Offset(ledX, ledY)
            )

            // 6. Surface-Mount Decoupling Capacitors (SMD components on side)
            drawRoundRect(
                color = MetallicSilverDark,
                topLeft = Offset(cx - halfSide + 12f, cy + halfSide - 16f),
                size = Size(10f, 5f),
                cornerRadius = CornerRadius(1f, 1f)
            )
            drawRoundRect(
                color = IndustrialAmber.copy(alpha = 0.7f),
                topLeft = Offset(cx - halfSide + 25f, cy + halfSide - 16f),
                size = Size(8f, 5f),
                cornerRadius = CornerRadius(1f, 1f)
            )
        }
    }
}
