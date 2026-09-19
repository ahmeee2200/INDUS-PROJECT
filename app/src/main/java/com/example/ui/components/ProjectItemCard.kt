package com.example.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun ProjectItemCard(
    project: ProjectEntity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardElevation by animateDpAsState(
        targetValue = if (isPressed) 6.dp else 2.dp,
        label = "elevation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Graphite900),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        border = BorderStroke(
            1.dp,
            if (isPressed) IndustrialAmber else Graphite800
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header Row: Code, Category, Bench
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Graphite950,
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, Graphite700)
                    ) {
                        Text(
                            text = project.projectCode,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = IndustrialAmberBright
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Category Indicator
                    Surface(
                        color = when (project.category) {
                            ProjectCategory.HARDWARE -> IndustrialAmberDeep
                            ProjectCategory.SOFTWARE -> Graphite850
                            ProjectCategory.HYBRID_IOT -> Graphite800
                        },
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(
                            1.dp,
                            when (project.category) {
                                ProjectCategory.HARDWARE -> IndustrialAmber.copy(alpha = 0.5f)
                                ProjectCategory.SOFTWARE -> MetallicSilver.copy(alpha = 0.4f)
                                ProjectCategory.HYBRID_IOT -> IndustrialAmberBright.copy(alpha = 0.4f)
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (project.category) {
                                    ProjectCategory.HARDWARE -> Icons.Default.DeveloperBoard
                                    ProjectCategory.SOFTWARE -> Icons.Default.Terminal
                                    ProjectCategory.HYBRID_IOT -> Icons.Default.Memory
                                },
                                contentDescription = null,
                                modifier = Modifier.size(11.dp),
                                tint = when (project.category) {
                                    ProjectCategory.HARDWARE -> IndustrialAmber
                                    ProjectCategory.SOFTWARE -> Ivory100
                                    ProjectCategory.HYBRID_IOT -> IndustrialAmberBright
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (project.category) {
                                    ProjectCategory.HARDWARE -> "HARDWARE"
                                    ProjectCategory.SOFTWARE -> "SOFTWARE"
                                    ProjectCategory.HYBRID_IOT -> "HYBRID IoT"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = Ivory100
                            )
                        }
                    }
                }

                // Bench Tag
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PinDrop,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MetallicSilverDark
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = project.benchLocation,
                        style = MaterialTheme.typography.labelSmall,
                        color = MetallicSilver
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Project Title
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleMedium,
                color = Ivory50,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Department / Lab
            Text(
                text = project.labDepartment,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MetallicSilverDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Key Modules / Stack preview box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Graphite950,
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, Graphite850)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    if (project.hardwareModules.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "HW: ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = IndustrialAmber
                            )
                            Text(
                                text = project.hardwareModules,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                color = Ivory200,
                                maxLines = 1
                            )
                        }
                    }
                    if (project.softwareStack.isNotBlank()) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "SW: ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = MetallicSilver
                            )
                            Text(
                                text = project.softwareStack,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                color = Ivory200,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar & Meta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FYP MILESTONES: ${project.completionPercent}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = MetallicSilver
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MetallicSilverDark
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${project.documentsCount} DOCS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MetallicSilver
                    )

                    if (project.ocrVerified) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(LabSoftwareVerified)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { project.completionPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = IndustrialAmber,
                trackColor = Graphite800
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Footer: Lead Student, Supervisor, Chevron
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Lead: ${project.leadStudent}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                        color = Ivory100
                    )
                    Text(
                        text = "Sup: ${project.supervisorName}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = MetallicSilverDark
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Graphite850,
                    border = BorderStroke(1.dp, Graphite700)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "INSPECT",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Ivory100
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = IndustrialAmber
                        )
                    }
                }
            }
        }
    }
}
