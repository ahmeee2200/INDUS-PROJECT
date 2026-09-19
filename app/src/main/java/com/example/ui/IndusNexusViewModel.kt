package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.IndusNexusDatabase
import com.example.data.IndusNexusRepository
import com.example.data.model.DocumentEntity
import com.example.data.model.DocumentType
import com.example.data.model.OcrProcessingStatus
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectEntity
import com.example.data.model.ProjectStatus
import com.example.ui.components.UserLabRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class IndusNexusUiState(
    val projects: List<ProjectEntity> = emptyList(),
    val filteredProjects: List<ProjectEntity> = emptyList(),
    val allDocuments: List<DocumentEntity> = emptyList(),
    val selectedCategory: ProjectCategory? = null,
    val searchQuery: String = "",
    val activeRole: UserLabRole = UserLabRole.STUDENT,
    val selectedProjectForInspection: ProjectEntity? = null,
    val documentsForSelectedProject: List<DocumentEntity> = emptyList(),
    val isOcrScannerOpen: Boolean = false,
    val isAddProjectOpen: Boolean = false,
    val hardwareCount: Int = 0,
    val softwareCount: Int = 0,
    val hybridCount: Int = 0,
    val verifiedOcrCount: Int = 0
)

data class FilterState(
    val category: ProjectCategory?,
    val query: String,
    val role: UserLabRole
)

data class DialogState(
    val selectedProject: ProjectEntity?,
    val isOcrOpen: Boolean,
    val isAddOpen: Boolean
)

class IndusNexusViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: IndusNexusRepository

    private val _selectedCategory = MutableStateFlow<ProjectCategory?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _activeRole = MutableStateFlow(UserLabRole.STUDENT)
    private val _selectedProject = MutableStateFlow<ProjectEntity?>(null)
    private val _isOcrScannerOpen = MutableStateFlow(false)
    private val _isAddProjectOpen = MutableStateFlow(false)

    init {
        val db = IndusNexusDatabase.getInstance(application)
        repository = IndusNexusRepository(db.projectDao(), db.documentDao())
        viewModelScope.launch {
            repository.ensureInitialDataSeeded()
        }
    }

    private val filterState = combine(_selectedCategory, _searchQuery, _activeRole) { cat, q, role ->
        FilterState(cat, q, role)
    }

    private val dialogState = combine(_selectedProject, _isOcrScannerOpen, _isAddProjectOpen) { proj, ocr, add ->
        DialogState(proj, ocr, add)
    }

    val uiState: StateFlow<IndusNexusUiState> = combine(
        repository.allProjects,
        repository.allDocuments,
        filterState,
        dialogState
    ) { projects, docs, filters, dialogs ->
        val cat = filters.category
        val query = filters.query
        val role = filters.role
        val selProj = dialogs.selectedProject
        val ocrOpen = dialogs.isOcrOpen
        val addOpen = dialogs.isAddOpen

        val filtered = projects.filter { proj ->
            val matchesCat = (cat == null || proj.category == cat)
            val matchesQuery = query.isBlank() ||
                    proj.title.contains(query, ignoreCase = true) ||
                    proj.projectCode.contains(query, ignoreCase = true) ||
                    proj.hardwareModules.contains(query, ignoreCase = true) ||
                    proj.softwareStack.contains(query, ignoreCase = true) ||
                    proj.benchLocation.contains(query, ignoreCase = true)
            matchesCat && matchesQuery
        }

        val projectDocs = if (selProj != null) {
            docs.filter { it.projectId == selProj.id }
        } else {
            emptyList()
        }

        IndusNexusUiState(
            projects = projects,
            filteredProjects = filtered,
            allDocuments = docs,
            selectedCategory = cat,
            searchQuery = query,
            activeRole = role,
            selectedProjectForInspection = selProj,
            documentsForSelectedProject = projectDocs,
            isOcrScannerOpen = ocrOpen,
            isAddProjectOpen = addOpen,
            hardwareCount = projects.count { it.category == ProjectCategory.HARDWARE },
            softwareCount = projects.count { it.category == ProjectCategory.SOFTWARE },
            hybridCount = projects.count { it.category == ProjectCategory.HYBRID_IOT },
            verifiedOcrCount = docs.count { it.ocrStatus == OcrProcessingStatus.VERIFIED }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = IndusNexusUiState()
    )

    fun selectCategory(category: ProjectCategory?) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectRole(role: UserLabRole) {
        _activeRole.value = role
    }

    fun inspectProject(project: ProjectEntity?) {
        _selectedProject.value = project
    }

    fun setOcrScannerOpen(open: Boolean) {
        _isOcrScannerOpen.value = open
    }

    fun setAddProjectOpen(open: Boolean) {
        _isAddProjectOpen.value = open
    }

    fun createProject(project: ProjectEntity) {
        viewModelScope.launch {
            val newId = repository.insertProject(project)
            repository.insertDocument(
                DocumentEntity(
                    projectId = newId,
                    title = "${project.projectCode}_Initial_System_Architecture.pdf",
                    docType = DocumentType.SCHEMATIC,
                    ocrStatus = OcrProcessingStatus.PROCESSING,
                    extractedSummary = "System block diagram & hardware pinout mapped for ${project.title}.",
                    fileSizeBytes = "1.8 MB",
                    confidenceScore = 0.92f
                )
            )
        }
    }

    fun supervisorSignOff(project: ProjectEntity) {
        viewModelScope.launch {
            val updated = project.copy(
                status = ProjectStatus.READY_FOR_DEFENSE,
                completionPercent = 100,
                ocrVerified = true
            )
            repository.updateProject(updated)
            _selectedProject.value = updated
        }
    }

    fun saveScannedDocument(
        projectId: Long,
        title: String,
        summary: String,
        confidence: Float
    ) {
        viewModelScope.launch {
            repository.insertDocument(
                DocumentEntity(
                    projectId = projectId,
                    title = title,
                    docType = DocumentType.OCR_REPORT,
                    ocrStatus = OcrProcessingStatus.VERIFIED,
                    extractedSummary = summary,
                    fileSizeBytes = "2.4 MB",
                    confidenceScore = confidence
                )
            )
        }
    }

    suspend fun ensureSeeded() {
        repository.ensureInitialDataSeeded()
    }
}
