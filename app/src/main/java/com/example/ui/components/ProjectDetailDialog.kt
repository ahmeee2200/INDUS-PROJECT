package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.DocumentEntity
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectEntity
import com.example.data.model.ProjectStatus
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

@Composable
fun ProjectDetailDialog(
    project: ProjectEntity,
    documents: List<DocumentEntity>,
    onDismiss: () -> Unit,
    onSignOff: (ProjectEntity) -> Unit,
    onTriggerScanForProject: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.90f)
                .clip(RoundedCornerShape(14.dp))
                .background(Graphite950)
                .border(1.dp, Graphite800, RoundedCornerShape(14.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Action Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Graphite900,
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(1.dp, IndustrialAmber)
                        ) {
                            Text(
                                text = project.projectCode,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = IndustrialAmberBright
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "PROJECT TELEMETRY INSPECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetallicSilver
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("close_project_detail_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MetallicSilver,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title and department
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 20.sp),
                    color = Ivory50
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${project.labDepartment} • Location: ${project.benchLocation}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MetallicSilver
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive 3D Microcontroller / Prototype Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Graphite900),
                    border = BorderStroke(1.dp, Graphite800)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "3D HARDWARE TELEMETRY & PINOUT",
                                style = MaterialTheme.typography.labelSmall,
                                color = IndustrialAmber
                            )
                            Text(
                                text = "Drag to tilt / Inspect",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = MetallicSilverDark
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Interactive 3D Canvas
                        Microcontroller3DCanvas(
                            modifier = Modifier.size(150.dp),
                            label = project.projectCode
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Active Processing Core: STM32H7 / ARM Cortex-M7 (Dual 480MHz)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Ivory200
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Hardware BOM & Software Stack Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Hardware Modules
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Graphite900),
                        border = BorderStroke(1.dp, Graphite800)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.DeveloperBoard,
                                    contentDescription = null,
                                    tint = IndustrialAmber,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "HARDWARE BOM",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Ivory100
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = project.hardwareModules,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = Ivory200
                            )
                        }
                    }

                    // Software Stack
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Graphite900),
                        border = BorderStroke(1.dp, Graphite800)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Terminal,
                                    contentDescription = null,
                                    tint = MetallicSilver,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "SOFTWARE STACK",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Ivory100
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = project.softwareStack,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = Ivory200
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Personnel / Academic Governance
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Graphite900),
                    border = BorderStroke(1.dp, Graphite800)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "LEAD STUDENT",
                                style = MaterialTheme.typography.labelSmall,
                                color = MetallicSilverDark
                            )
                            Text(
                                text = project.leadStudent,
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp),
                                color = Ivory50
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "FACULTY SUPERVISOR",
                                style = MaterialTheme.typography.labelSmall,
                                color = MetallicSilverDark
                            )
                            Text(
                                text = project.supervisorName,
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp),
                                color = IndustrialAmberBright
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "TEAM SIZE",
                                style = MaterialTheme.typography.labelSmall,
                                color = MetallicSilverDark
                            )
                            Text(
                                text = "${project.teamMembersCount} Engineers",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = Ivory100
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "STATUS",
                                style = MaterialTheme.typography.labelSmall,
                                color = MetallicSilverDark
                            )
                            Text(
                                text = project.status.name.replace("_", " "),
                                style = MaterialTheme.typography.labelSmall,
                                color = LabSoftwareVerified
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Associated Documents & OCR Verification
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LABORATORY DOCUMENTATION & OCR AUDIT",
                        style = MaterialTheme.typography.labelSmall,
                        color = MetallicSilver
                    )

                    OutlinedButton(
                        onClick = onTriggerScanForProject,
                        modifier = Modifier.testTag("scan_project_document_button"),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = IndustrialAmber),
                        border = BorderStroke(1.dp, IndustrialAmber)
                    ) {
                        Text(
                            text = "+ SCAN DOCUMENT",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (documents.isEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Graphite900,
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, Graphite800)
                    ) {
                        Text(
                            text = "No documents scanned yet. Use OCR scan to ingest lab schematics.",
                            modifier = Modifier.padding(14.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MetallicSilverDark
                        )
                    }
                } else {
                    documents.forEach { doc ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            color = Graphite900,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, Graphite800)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = doc.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                                        color = Ivory100
                                    )
                                    Surface(
                                        color = Graphite850,
                                        shape = RoundedCornerShape(3.dp),
                                        border = BorderStroke(1.dp, LabSoftwareVerified)
                                    ) {
                                        Text(
                                            text = "${doc.ocrStatus.name} (${(doc.confidenceScore * 100).toInt()}%)",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp),
                                            color = LabSoftwareVerified
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = doc.extractedSummary,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                    color = Ivory200
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Bottom Controls: Supervisor Sign-off & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onSignOff(project)
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("supervisor_signoff_button"),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndustrialAmber,
                            contentColor = Graphite950
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SUPERVISOR SIGN-OFF",
                            style = MaterialTheme.typography.labelLarge.copy(color = Graphite950)
                        )
                    }
                }
            }
        }
    }
}
