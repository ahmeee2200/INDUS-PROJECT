package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ProjectCategory
import com.example.ui.components.AddProjectDialog
import com.example.ui.components.LabControlHeader
import com.example.ui.components.OcrScannerModal
import com.example.ui.components.ProjectCore3DView
import com.example.ui.components.ProjectDetailDialog
import com.example.ui.components.ProjectItemCard
import com.example.ui.components.UserLabRole
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
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark

@Composable
fun IndusNexusScreen(
    viewModel: IndusNexusViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Graphite950,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.setAddProjectOpen(true) },
                modifier = Modifier
                    .testTag("add_project_fab")
                    .windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = IndustrialAmber,
                contentColor = Graphite950,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Project",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "NEW PROJECT",
                        style = MaterialTheme.typography.labelLarge.copy(color = Graphite950)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Laboratory Header with Role Toggle
            LabControlHeader(
                currentRole = uiState.activeRole,
                onRoleSelected = { viewModel.selectRole(it) }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Central 3D "Project Core" Hero Section
                item {
                    ProjectCore3DView(
                        activeProjectsCount = uiState.projects.size,
                        onSelectCategory = { cat -> viewModel.selectCategory(cat) },
                        onTriggerOcrScanner = { viewModel.setOcrScannerOpen(true) }
                    )
                }

                // Search & Filter Controls
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = uiState.searchQuery,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("search_projects_input"),
                            placeholder = {
                                Text(
                                    "Search hardware, software, microcontrollers, bench IDs...",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = MetallicSilverDark
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = MetallicSilver,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                if (uiState.searchQuery.isNotBlank()) {
                                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear",
                                            tint = MetallicSilverDark,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = IndustrialAmber,
                                unfocusedBorderColor = Graphite800,
                                focusedTextColor = Ivory50,
                                unfocusedTextColor = Ivory100,
                                focusedContainerColor = Graphite900,
                                unfocusedContainerColor = Graphite900
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Category Filter Switcher Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterTabItem(
                                label = "ALL (${uiState.projects.size})",
                                isSelected = uiState.selectedCategory == null,
                                icon = Icons.Default.FilterList,
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.selectCategory(null) }
                            )

                            FilterTabItem(
                                label = "HW (${uiState.hardwareCount})",
                                isSelected = uiState.selectedCategory == ProjectCategory.HARDWARE,
                                icon = Icons.Default.DeveloperBoard,
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.selectCategory(ProjectCategory.HARDWARE) }
                            )

                            FilterTabItem(
                                label = "SW (${uiState.softwareCount})",
                                isSelected = uiState.selectedCategory == ProjectCategory.SOFTWARE,
                                icon = Icons.Default.Terminal,
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.selectCategory(ProjectCategory.SOFTWARE) }
                            )

                            FilterTabItem(
                                label = "IoT (${uiState.hybridCount})",
                                isSelected = uiState.selectedCategory == ProjectCategory.HYBRID_IOT,
                                icon = Icons.Default.Memory,
                                modifier = Modifier.weight(1f),
                                onClick = { viewModel.selectCategory(ProjectCategory.HYBRID_IOT) }
                            )
                        }
                    }
                }

                // Section Title Row: Current Stream Status
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (uiState.selectedCategory) {
                                null -> "ACTIVE LABORATORY PROJECTS (${uiState.filteredProjects.size})"
                                ProjectCategory.HARDWARE -> "HARDWARE & EMBEDDED PROJECTS (${uiState.filteredProjects.size})"
                                ProjectCategory.SOFTWARE -> "SOFTWARE & EDGE AI PROJECTS (${uiState.filteredProjects.size})"
                                ProjectCategory.HYBRID_IOT -> "HYBRID IoT & SENSOR MESH (${uiState.filteredProjects.size})"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            letterSpacing = 1.sp,
                            color = MetallicSilver
                        )

                        // Direct OCR quick trigger
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { viewModel.setOcrScannerOpen(true) },
                            color = Graphite850,
                            border = BorderStroke(1.dp, Graphite700)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DocumentScanner,
                                    contentDescription = null,
                                    tint = IndustrialAmber,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "SCAN OCR",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = Ivory100
                                )
                            }
                        }
                    }
                }

                // Project Cards List
                if (uiState.filteredProjects.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Graphite900),
                            border = BorderStroke(1.dp, Graphite800)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No laboratory projects match current filter.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Ivory200
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        viewModel.selectCategory(null)
                                        viewModel.updateSearchQuery("")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Graphite800)
                                ) {
                                    Text(
                                        text = "Reset Filters",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Ivory100
                                    )
                                }
                            }
                        }
                    }
                } else {
                    items(
                        items = uiState.filteredProjects,
                        key = { it.id }
                    ) { project ->
                        ProjectItemCard(
                            project = project,
                            modifier = Modifier.testTag("project_item_${project.projectCode}"),
                            onClick = { viewModel.inspectProject(project) }
                        )
                    }
                }
            }
        }
    }

    // Inspection Dialog
    if (uiState.selectedProjectForInspection != null) {
        ProjectDetailDialog(
            project = uiState.selectedProjectForInspection!!,
            documents = uiState.documentsForSelectedProject,
            onDismiss = { viewModel.inspectProject(null) },
            onSignOff = { proj -> viewModel.supervisorSignOff(proj) },
            onTriggerScanForProject = {
                viewModel.setOcrScannerOpen(true)
            }
        )
    }

    // Add Project Dialog
    if (uiState.isAddProjectOpen) {
        AddProjectDialog(
            onDismiss = { viewModel.setAddProjectOpen(false) },
            onProjectCreated = { newProj -> viewModel.createProject(newProj) }
        )
    }

    // OCR Scanner Modal
    if (uiState.isOcrScannerOpen) {
        OcrScannerModal(
            projects = uiState.projects,
            onDismiss = { viewModel.setOcrScannerOpen(false) },
            onSaveScan = { projId, title, summary, conf ->
                viewModel.saveScannedDocument(projId, title, summary, conf)
            }
        )
    }
}

@Composable
private fun FilterTabItem(
    label: String,
    isSelected: Boolean,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) IndustrialAmberDeep else Graphite900,
        border = BorderStroke(
            1.dp,
            if (isSelected) IndustrialAmber else Graphite800
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) IndustrialAmberBright else MetallicSilverDark,
                modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                color = if (isSelected) IndustrialAmberBright else MetallicSilver
            )
        }
    }
}
