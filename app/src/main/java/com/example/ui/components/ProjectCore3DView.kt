package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.ProjectCategory
import com.example.ui.theme.BrushedAluminum
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

data class CoreNodeItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val category: ProjectCategory? = null,
    val isOcrAction: Boolean = false
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectCore3DView(
    modifier: Modifier = Modifier,
    activeProjectsCount: Int = 6,
    onSelectCategory: (ProjectCategory?) -> Unit = {},
    onTriggerOcrScanner: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "coreTelemetry")
    val coreGlowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowPulse"
    )

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
            // Lab Header & Institution Identity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(IndustrialAmber)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "INDUS UNIVERSITY",
                            style = MaterialTheme.typography.labelSmall,
                            letterSpacing = 1.8.sp,
                            color = MetallicSilver
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "INDUS NEXUS",
                        style = MaterialTheme.typography.displayMedium,
                        color = Ivory50,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "University Project & Academic Management System",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Ivory200
                    )
                }

                // Control Center Status Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Graphite850,
                    border = BorderStroke(1.dp, Graphite700)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(LabSoftwareVerified)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LAB CORE V4.8",
                            style = MaterialTheme.typography.labelSmall,
                            color = Ivory100
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Central 3D "Project Core" Rendering Frame
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Graphite950)
                    .border(BorderStroke(1.dp, Graphite800), RoundedCornerShape(8.dp))
            ) {
                // Background 3D Photorealistic Core Rendering
                Image(
                    painter = painterResource(id = R.drawable.img_lab_project_core),
                    contentDescription = "Indus Nexus Laboratory 3D Project Core",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // High-precision engineering gradient vignette
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Graphite950.copy(alpha = 0.4f),
                                    Color.Transparent,
                                    Graphite950.copy(alpha = 0.85f)
                                )
                            )
                        )
                )

                // Subdued PCB Circuit overlay canvas
                PcbTraceCanvas(
                    modifier = Modifier.fillMaxSize(),
                    traceColor = Graphite700.copy(alpha = 0.5f),
                    pulseColor = IndustrialAmberBright
                )

                // Center Project Core Hub Overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Surface(
                        color = Graphite900.copy(alpha = 0.92f),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, IndustrialAmber.copy(alpha = coreGlowAlpha))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Memory,
                                contentDescription = null,
                                tint = IndustrialAmber,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "PROJECT CORE • ACTIVE SYNC",
                                style = MaterialTheme.typography.labelSmall,
                                color = IndustrialAmberBright
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hardware & Software Laboratory Node Hub",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Ivory50
                    )
                }

                // Top Right Telemetry Readout
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp),
                    color = Graphite950.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, Graphite800)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "BENCH BUS: 100 Mbps CAN-FD",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetallicSilver
                        )
                        Text(
                            text = "SYNCHRONIZED: $activeProjectsCount PROJECTS",
                            style = MaterialTheme.typography.labelSmall,
                            color = IndustrialAmber
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Connected Data Nodes Grid (Hardware, Software, Documentation, OCR, Students, Supervisors)
            Text(
                text = "CONNECTED LABORATORY SUBSYSTEMS",
                style = MaterialTheme.typography.labelSmall,
                color = MetallicSilver,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val nodes = listOf(
                CoreNodeItem("Hardware Projects", "Microcontrollers & PCBs", Icons.Default.DeveloperBoard, ProjectCategory.HARDWARE),
                CoreNodeItem("Software Projects", "Edge AI & Real-time Stack", Icons.Default.Terminal, ProjectCategory.SOFTWARE),
                CoreNodeItem("Hybrid IoT Nodes", "Telemetry & Actuators", Icons.Default.Memory, ProjectCategory.HYBRID_IOT),
                CoreNodeItem("OCR Scanner", "Optical Report Extraction", Icons.Default.QrCodeScanner, isOcrAction = true),
                CoreNodeItem("Project Documents", "Schematics & BOMs", Icons.Default.Description),
                CoreNodeItem("Supervisors & FYP", "Faculty Evaluations", Icons.Default.School)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 2
            ) {
                nodes.forEach { node ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                if (node.isOcrAction) {
                                    onTriggerOcrScanner()
                                } else if (node.category != null) {
                                    onSelectCategory(node.category)
                                } else {
                                    onSelectCategory(null)
                                }
                            },
                        shape = RoundedCornerShape(6.dp),
                        color = Graphite850,
                        border = BorderStroke(1.dp, Graphite800)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Graphite900)
                                    .border(1.dp, Graphite700, RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = node.icon,
                                    contentDescription = node.title,
                                    tint = if (node.isOcrAction) IndustrialAmberBright else Ivory100,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(9.dp))
                            Column {
                                Text(
                                    text = node.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                                    fontWeight = FontWeight.SemiBold,
                                    color = Ivory100
                                )
                                Text(
                                    text = node.subtitle,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                                    color = MetallicSilverDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
