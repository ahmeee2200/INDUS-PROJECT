package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ProjectEntity
import com.example.ui.theme.Graphite700
import com.example.ui.theme.Graphite800
import com.example.ui.theme.Graphite850
import com.example.ui.theme.Graphite900
import com.example.ui.theme.Graphite950
import com.example.ui.theme.IndustrialAmber
import com.example.ui.theme.IndustrialAmberBright
import com.example.ui.theme.Ivory100
import com.example.ui.theme.Ivory200
import com.example.ui.theme.Ivory50
import com.example.ui.theme.LabSoftwareVerified
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark

@Composable
fun OcrScannerModal(
    projects: List<ProjectEntity>,
    onDismiss: () -> Unit,
    onSaveScan: (projectId: Long, docTitle: String, summary: String, confidence: Float) -> Unit
) {
    var selectedProject by remember {
        mutableStateOf(projects.firstOrNull())
    }
    var scanResultSummary by remember { mutableStateOf<String?>(null) }
    var scanConfidence by remember { mutableStateOf(0.98f) }

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
                // Header
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
                                text = "LAB OCR SUBSYSTEM",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = IndustrialAmberBright
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "OPTICAL REPORT INGESTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = MetallicSilver
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MetallicSilver
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Automated Document & Schematic Recognition",
                    style = MaterialTheme.typography.titleLarge,
                    color = Ivory50
                )
                Text(
                    text = "Laser scan engineering schematics, bench calibration logs, and supervisor evaluation forms.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Ivory200
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Project Selector Row
                Text(
                    text = "TARGET PROJECT FOLDER",
                    style = MaterialTheme.typography.labelSmall,
                    color = MetallicSilverDark
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    projects.take(3).forEach { proj ->
                        val isSelected = selectedProject?.id == proj.id
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { selectedProject = proj },
                            color = if (isSelected) Graphite850 else Graphite900,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) IndustrialAmber else Graphite800
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = proj.projectCode,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = if (isSelected) IndustrialAmberBright else Ivory200
                                )
                                Text(
                                    text = proj.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
                                    color = Ivory100,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Embedded OCR Scanner
                OcrScannerView(
                    projectTitle = selectedProject?.title ?: "Laboratory Hardware Schematic",
                    onOcrCompleted = { summary, conf ->
                        scanResultSummary = summary
                        scanConfidence = conf
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (scanResultSummary != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Graphite900),
                        border = BorderStroke(1.dp, LabSoftwareVerified)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = LabSoftwareVerified,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "OCR EXTRACTION VERIFIED // CONFIDENCE: ${(scanConfidence * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = LabSoftwareVerified
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = scanResultSummary!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Ivory100
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val proj = selectedProject
                            if (proj != null && scanResultSummary != null) {
                                onSaveScan(
                                    proj.id,
                                    "${proj.projectCode}_Optical_Scan_Report.pdf",
                                    scanResultSummary!!,
                                    scanConfidence
                                )
                                onDismiss()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("commit_ocr_button"),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndustrialAmber,
                            contentColor = Graphite950
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.DocumentScanner,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "COMMIT OCR REPORT TO PROJECT",
                            style = MaterialTheme.typography.labelLarge.copy(color = Graphite950)
                        )
                    }
                }
            }
        }
    }
}
