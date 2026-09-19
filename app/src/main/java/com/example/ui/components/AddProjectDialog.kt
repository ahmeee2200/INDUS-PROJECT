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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ui.theme.Ivory100
import com.example.ui.theme.Ivory200
import com.example.ui.theme.Ivory50
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark

@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onProjectCreated: (ProjectEntity) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(ProjectCategory.HARDWARE) }
    var department by remember { mutableStateOf("Robotics & Mechatronics Lab") }
    var hardwareModules by remember { mutableStateOf("STM32H743ZI, 9-DOF IMU, CAN-FD") }
    var softwareStack by remember { mutableStateOf("FreeRTOS, C/C++, ROS2 Humble") }
    var benchLocation by remember { mutableStateOf("BENCH-05A") }
    var supervisorName by remember { mutableStateOf("Dr. Tariq Mahmood, CEng") }
    var leadStudent by remember { mutableStateOf("") }

    val codePrefix = when (category) {
        ProjectCategory.HARDWARE -> "INDUS-HW-"
        ProjectCategory.SOFTWARE -> "INDUS-SW-"
        ProjectCategory.HYBRID_IOT -> "INDUS-IOT-"
    }
    val randomId = remember { (200..999).random() }
    val generatedCode = "$codePrefix$randomId"

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
                // Top Header
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
                                text = generatedCode,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = IndustrialAmberBright
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "REGISTER NEW LAB PROJECT",
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
                    text = "Engineering Laboratory Project Intake",
                    style = MaterialTheme.typography.titleLarge,
                    color = Ivory50
                )
                Text(
                    text = "Assign laboratory bench, hardware prototype components, and academic supervisor.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Ivory200
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Category Selection Chips
                Text(
                    text = "PROJECT DOMAIN",
                    style = MaterialTheme.typography.labelSmall,
                    color = MetallicSilverDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ProjectCategory.values().forEach { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = {
                                Text(
                                    text = cat.name.replace("_", " "),
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = IndustrialAmber,
                                selectedLabelColor = Graphite950,
                                containerColor = Graphite900,
                                labelColor = Ivory200
                            ),
                            border = BorderStroke(1.dp, if (category == cat) IndustrialAmber else Graphite800)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Project Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Project Title (e.g. High-Speed FPGA Data Logger)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("project_title_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndustrialAmber,
                        unfocusedBorderColor = Graphite700,
                        focusedTextColor = Ivory50,
                        unfocusedTextColor = Ivory100,
                        focusedLabelColor = IndustrialAmber,
                        unfocusedLabelColor = MetallicSilver
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Department
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    label = { Text("Laboratory / Department") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndustrialAmber,
                        unfocusedBorderColor = Graphite700,
                        focusedTextColor = Ivory50,
                        unfocusedTextColor = Ivory100,
                        focusedLabelColor = IndustrialAmber,
                        unfocusedLabelColor = MetallicSilver
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Hardware Modules (BOM)
                OutlinedTextField(
                    value = hardwareModules,
                    onValueChange = { hardwareModules = it },
                    label = { Text("Hardware Modules / BOM (Microcontrollers, Sensors, ICs)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndustrialAmber,
                        unfocusedBorderColor = Graphite700,
                        focusedTextColor = Ivory50,
                        unfocusedTextColor = Ivory100,
                        focusedLabelColor = IndustrialAmber,
                        unfocusedLabelColor = MetallicSilver
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Software Stack
                OutlinedTextField(
                    value = softwareStack,
                    onValueChange = { softwareStack = it },
                    label = { Text("Software & Firmware Stack (RTOS, C/C++, ROS2, AI)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndustrialAmber,
                        unfocusedBorderColor = Graphite700,
                        focusedTextColor = Ivory50,
                        unfocusedTextColor = Ivory100,
                        focusedLabelColor = IndustrialAmber,
                        unfocusedLabelColor = MetallicSilver
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Bench Location & Lead Student
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = benchLocation,
                        onValueChange = { benchLocation = it },
                        label = { Text("Lab Bench ID") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IndustrialAmber,
                            unfocusedBorderColor = Graphite700,
                            focusedTextColor = Ivory50,
                            unfocusedTextColor = Ivory100,
                            focusedLabelColor = IndustrialAmber,
                            unfocusedLabelColor = MetallicSilver
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = leadStudent,
                        onValueChange = { leadStudent = it },
                        label = { Text("Lead Student (ID)") },
                        modifier = Modifier.weight(1.5f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IndustrialAmber,
                            unfocusedBorderColor = Graphite700,
                            focusedTextColor = Ivory50,
                            unfocusedTextColor = Ivory100,
                            focusedLabelColor = IndustrialAmber,
                            unfocusedLabelColor = MetallicSilver
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Supervisor
                OutlinedTextField(
                    value = supervisorName,
                    onValueChange = { supervisorName = it },
                    label = { Text("Faculty Supervisor") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndustrialAmber,
                        unfocusedBorderColor = Graphite700,
                        focusedTextColor = Ivory50,
                        unfocusedTextColor = Ivory100,
                        focusedLabelColor = IndustrialAmber,
                        unfocusedLabelColor = MetallicSilver
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            val newProject = ProjectEntity(
                                projectCode = generatedCode,
                                title = title,
                                category = category,
                                labDepartment = department,
                                status = ProjectStatus.IN_PROGRESS,
                                supervisorName = supervisorName,
                                leadStudent = if (leadStudent.isBlank()) "Engineering Lead (F22-EE)" else leadStudent,
                                teamMembersCount = 3,
                                hardwareModules = hardwareModules,
                                softwareStack = softwareStack,
                                benchLocation = benchLocation,
                                completionPercent = 25,
                                documentsCount = 1,
                                ocrVerified = false
                            )
                            onProjectCreated(newProject)
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("submit_register_project_button"),
                    enabled = title.isNotBlank(),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialAmber,
                        contentColor = Graphite950
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.DeveloperBoard,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "REGISTER IN LABORATORY REPOSITORY",
                        style = MaterialTheme.typography.labelLarge.copy(color = Graphite950)
                    )
                }
            }
        }
    }
}
