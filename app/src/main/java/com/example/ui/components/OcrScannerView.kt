package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Graphite700
import com.example.ui.theme.Graphite800
import com.example.ui.theme.Graphite850
import com.example.ui.theme.Graphite900
import com.example.ui.theme.Graphite950
import com.example.ui.theme.IndustrialAmber
import com.example.ui.theme.IndustrialAmberBright
import com.example.ui.theme.IndustrialAmberDeep
import com.example.ui.theme.Ivory100
import com.example.ui.theme.Ivory200
import com.example.ui.theme.Ivory50
import com.example.ui.theme.LabSoftwareVerified
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import kotlinx.coroutines.delay

@Composable
fun OcrScannerView(
    modifier: Modifier = Modifier,
    projectTitle: String = "INDUS-HW-401 Quadruped Controller",
    onOcrCompleted: (String, Float) -> Unit = { _, _ -> }
) {
    var isScanning by remember { mutableStateOf(false) }
    var scanCompleted by remember { mutableStateOf(false) }
    var recognizedTokens by remember { mutableStateOf<List<String>>(emptyList()) }

    val infiniteTransition = rememberInfiniteTransition(label = "ocrLaser")
    val laserYRatio by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserY"
    )

    LaunchedEffect(isScanning) {
        if (isScanning) {
            scanCompleted = false
            recognizedTokens = emptyList()
            delay(600)
            recognizedTokens = recognizedTokens + "INDUS UNIVERSITY // DEPT. OF ELECTRICAL & MECHATRONICS"
            delay(800)
            recognizedTokens = recognizedTokens + "DOCUMENT CLASSIFICATION: FYP HARDWARE SCHEMATIC REV 3.2"
            delay(700)
            recognizedTokens = recognizedTokens + "MICROCONTROLLER: STM32H743ZI / DUAL 480MHz BUS"
            delay(900)
            recognizedTokens = recognizedTokens + "SUPERVISOR AUDIT: DR. TARIQ MAHMOOD (VERIFIED STAMP)"
            delay(500)
            isScanning = false
            scanCompleted = true
            onOcrCompleted(
                "Document Rev 3.2 verified. STM32H7 pinout and faculty supervisor stamp confirmed.",
                0.985f
            )
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Graphite900),
        border = BorderStroke(1.dp, Graphite800)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // OCR Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DocumentScanner,
                        contentDescription = "OCR Scanner",
                        tint = IndustrialAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "OPTICAL REPORT & SCHEMATIC OCR",
                            style = MaterialTheme.typography.titleMedium,
                            color = Ivory50
                        )
                        Text(
                            text = "Target: $projectTitle",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetallicSilver
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (scanCompleted) Graphite850 else IndustrialAmberDeep,
                    border = BorderStroke(1.dp, if (scanCompleted) LabSoftwareVerified else IndustrialAmber)
                ) {
                    Text(
                        text = if (scanCompleted) "OCR VERIFIED" else if (isScanning) "SCANNING..." else "READY",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (scanCompleted) LabSoftwareVerified else IndustrialAmberBright
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Optical Viewport with laser scanner simulation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Graphite950)
                    .border(1.dp, Graphite800, RoundedCornerShape(8.dp))
            ) {
                // Technical Grid background
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Subtle grid lines
                    val step = 20.dp.toPx()
                    var x = 0f
                    while (x < w) {
                        drawLine(
                            color = Graphite800.copy(alpha = 0.4f),
                            start = Offset(x, 0f),
                            end = Offset(x, h),
                            strokeWidth = 0.5f
                        )
                        x += step
                    }
                    var y = 0f
                    while (y < h) {
                        drawLine(
                            color = Graphite800.copy(alpha = 0.4f),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 0.5f
                        )
                        y += step
                    }

                    // Simulated document lines
                    drawRoundRect(
                        color = Graphite850,
                        topLeft = Offset(w * 0.12f, h * 0.15f),
                        size = Size(w * 0.76f, 14f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )
                    drawRoundRect(
                        color = Graphite850,
                        topLeft = Offset(w * 0.12f, h * 0.28f),
                        size = Size(w * 0.55f, 10f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )
                    drawRoundRect(
                        color = Graphite850,
                        topLeft = Offset(w * 0.12f, h * 0.40f),
                        size = Size(w * 0.70f, 10f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )
                    drawRoundRect(
                        color = Graphite850,
                        topLeft = Offset(w * 0.12f, h * 0.52f),
                        size = Size(w * 0.62f, 10f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )
                    drawRoundRect(
                        color = Graphite850,
                        topLeft = Offset(w * 0.12f, h * 0.64f),
                        size = Size(w * 0.45f, 10f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
                    )

                    // Laser scan bar
                    if (isScanning) {
                        val currentY = h * laserYRatio
                        // Laser light line
                        drawLine(
                            color = IndustrialAmberBright,
                            start = Offset(0f, currentY),
                            end = Offset(w, currentY),
                            strokeWidth = 2.5f
                        )
                        // Laser glow band
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    IndustrialAmber.copy(alpha = 0.25f),
                                    Color.Transparent
                                ),
                                startY = currentY,
                                endY = currentY - 30f
                            ),
                            topLeft = Offset(0f, currentY - 30f),
                            size = Size(w, 30f)
                        )
                    }
                }

                // Extracted Tokens Overlay in the scanner viewport
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    recognizedTokens.takeLast(2).forEach { token ->
                        Surface(
                            modifier = Modifier.padding(bottom = 4.dp),
                            shape = RoundedCornerShape(3.dp),
                            color = Graphite900.copy(alpha = 0.9f),
                            border = BorderStroke(1.dp, IndustrialAmber.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = "► $token",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = IndustrialAmberBright
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Trigger Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { isScanning = true },
                    enabled = !isScanning,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("run_ocr_button"),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialAmber,
                        contentColor = Graphite950
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isScanning) "Extracting Data..." else "Run Document OCR",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp)
                    )
                }

                OutlinedButton(
                    onClick = {
                        isScanning = false
                        scanCompleted = false
                        recognizedTokens = emptyList()
                    },
                    modifier = Modifier.weight(0.6f),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Ivory100),
                    border = BorderStroke(1.dp, Graphite700)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MetallicSilver
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Reset",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}
