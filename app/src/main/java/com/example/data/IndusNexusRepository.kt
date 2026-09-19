package com.example.data

import com.example.data.dao.DocumentDao
import com.example.data.dao.ProjectDao
import com.example.data.model.DocumentEntity
import com.example.data.model.DocumentType
import com.example.data.model.OcrProcessingStatus
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectEntity
import com.example.data.model.ProjectStatus
import kotlinx.coroutines.flow.Flow

class IndusNexusRepository(
    private val projectDao: ProjectDao,
    private val documentDao: DocumentDao
) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()
    val allDocuments: Flow<List<DocumentEntity>> = documentDao.getAllDocuments()

    fun getProjectsByCategory(category: ProjectCategory): Flow<List<ProjectEntity>> =
        projectDao.getProjectsByCategory(category)

    fun getProjectById(id: Long): Flow<ProjectEntity?> =
        projectDao.getProjectById(id)

    fun getDocumentsForProject(projectId: Long): Flow<List<DocumentEntity>> =
        documentDao.getDocumentsForProject(projectId)

    suspend fun insertProject(project: ProjectEntity): Long =
        projectDao.insertProject(project)

    suspend fun updateProject(project: ProjectEntity) =
        projectDao.updateProject(project)

    suspend fun deleteProject(id: Long) =
        projectDao.deleteProjectById(id)

    suspend fun insertDocument(document: DocumentEntity): Long =
        documentDao.insertDocument(document)

    suspend fun ensureInitialDataSeeded() {
        if (projectDao.getProjectCount() == 0) {
            val sampleProjects = listOf(
                ProjectEntity(
                    projectCode = "INDUS-HW-401",
                    title = "Autonomous Quadruped Robot Hardware Controller",
                    category = ProjectCategory.HARDWARE,
                    labDepartment = "Robotics & Mechatronics Lab",
                    status = ProjectStatus.BENCH_TESTING,
                    supervisorName = "Dr. Tariq Mahmood, CEng",
                    leadStudent = "Hamza Tariq (F22-EE-084)",
                    teamMembersCount = 4,
                    hardwareModules = "STM32H743ZI Dual-Core, 12x FOC BLDC Drivers, RPLiDAR A3, IMU 9-DOF, CAN-FD",
                    softwareStack = "FreeRTOS Kernel, C/C++ Bare-Metal, ROS2 Humble Micro-XRCE",
                    benchLocation = "BENCH-04B",
                    completionPercent = 78,
                    documentsCount = 5,
                    ocrVerified = true
                ),
                ProjectEntity(
                    projectCode = "INDUS-SW-219",
                    title = "Distributed Edge AI Vision & Telemetry Hub",
                    category = ProjectCategory.SOFTWARE,
                    labDepartment = "Computer Systems & AI Lab",
                    status = ProjectStatus.READY_FOR_DEFENSE,
                    supervisorName = "Engr. Sarah Siddiqui, MIEEE",
                    leadStudent = "Ayesha Khan (F21-CS-112)",
                    teamMembersCount = 3,
                    hardwareModules = "NVIDIA Jetson Orin Nano, Dual CSI-2 Sony IMX477 Sensors, Gigabit PHY",
                    softwareStack = "PyTorch TensorRT, CUDA 12, FastAPI, Jetpack Compose Client, ZeroMQ",
                    benchLocation = "RACK-07A",
                    completionPercent = 94,
                    documentsCount = 8,
                    ocrVerified = true
                ),
                ProjectEntity(
                    projectCode = "INDUS-IOT-305",
                    title = "Smart Microgrid Power Inverter & Telemetry Gateway",
                    category = ProjectCategory.HYBRID_IOT,
                    labDepartment = "Power Electronics & Energy Lab",
                    status = ProjectStatus.IN_PROGRESS,
                    supervisorName = "Prof. Dr. Mansoor Ahmed",
                    leadStudent = "Bilal Farooq (F22-EE-042)",
                    teamMembersCount = 4,
                    hardwareModules = "TI C2000 TMS320F28379D DSP, GaN FET Half-Bridge, Current Hall Sensors",
                    softwareStack = "Embedded C, Modbus TCP, LoRaWAN Gateway Protocol, Grafana Telemetry",
                    benchLocation = "BENCH-12A",
                    completionPercent = 65,
                    documentsCount = 4,
                    ocrVerified = false
                ),
                ProjectEntity(
                    projectCode = "INDUS-HW-512",
                    title = "FPGA High-Speed Signal Acquisition & DSP Module",
                    category = ProjectCategory.HARDWARE,
                    labDepartment = "VLSI & Digital Systems Lab",
                    status = ProjectStatus.DOCUMENTATION_REVIEW,
                    supervisorName = "Dr. Tariq Mahmood, CEng",
                    leadStudent = "Usman Ghani (F21-EE-109)",
                    teamMembersCount = 2,
                    hardwareModules = "AMD Xilinx Artix-7 XC7A100T, Dual 65MSPS AD9226 ADCs, DDR3 256MB",
                    softwareStack = "SystemVerilog RTL, Vivado 2024.1, High-Speed DMA Drivers, Python GUI",
                    benchLocation = "BENCH-02C",
                    completionPercent = 88,
                    documentsCount = 6,
                    ocrVerified = true
                ),
                ProjectEntity(
                    projectCode = "INDUS-SW-188",
                    title = "Automated Lab OCR & Optical Report Extraction Core",
                    category = ProjectCategory.SOFTWARE,
                    labDepartment = "Computing & Software Innovation Lab",
                    status = ProjectStatus.READY_FOR_DEFENSE,
                    supervisorName = "Dr. M. Ashfaq, Head of CS",
                    leadStudent = "Fatima Ashfaq (F21-SE-003)",
                    teamMembersCount = 3,
                    hardwareModules = "Industrial High-Resolution Document Camera (Sony IMX298, Ring LED)",
                    softwareStack = "OpenCV 4, Tesseract OCR Engine, Android Room DB, Kotlin Coroutines",
                    benchLocation = "STATION-OCR-01",
                    completionPercent = 96,
                    documentsCount = 11,
                    ocrVerified = true
                ),
                ProjectEntity(
                    projectCode = "INDUS-IOT-417",
                    title = "Sub-GHz Environmental Sensor Mesh & Actuator Node",
                    category = ProjectCategory.HYBRID_IOT,
                    labDepartment = "Embedded Systems & IoT Lab",
                    status = ProjectStatus.IN_PROGRESS,
                    supervisorName = "Engr. Sarah Siddiqui, MIEEE",
                    leadStudent = "Zainab Ali (F22-EE-071)",
                    teamMembersCount = 3,
                    hardwareModules = "Nordic nRF52840, Bosch BME680 Gas/Temp Sensor, Semtech SX1262 LoRa",
                    softwareStack = "Zephyr RTOS, Bluetooth Mesh, C++17, CoAP / MQTT Security Layer",
                    benchLocation = "BENCH-08D",
                    completionPercent = 52,
                    documentsCount = 3,
                    ocrVerified = false
                )
            )
            projectDao.insertAllProjects(sampleProjects)

            val sampleDocuments = listOf(
                DocumentEntity(
                    projectId = 1,
                    title = "INDUS-HW-401_Schematic_Rev3.2_DifferentialPairs.pdf",
                    docType = DocumentType.SCHEMATIC,
                    ocrStatus = OcrProcessingStatus.VERIFIED,
                    extractedSummary = "Verified 4-layer FR4 impedance-matched PCB stackup. Pinout validated for STM32H7 CAN-FD bus and SPI3 transceiver lines.",
                    fileSizeBytes = "4.8 MB",
                    confidenceScore = 0.98f
                ),
                DocumentEntity(
                    projectId = 1,
                    title = "BLDC_Bench_Calibration_OCR_Report.pdf",
                    docType = DocumentType.OCR_REPORT,
                    ocrStatus = OcrProcessingStatus.VERIFIED,
                    extractedSummary = "OCR extracted 12-axis torque ripple curve and hall sensor offset values. Supervisor digital signature matched.",
                    fileSizeBytes = "2.1 MB",
                    confidenceScore = 0.95f
                ),
                DocumentEntity(
                    projectId = 2,
                    title = "EdgeAI_TensorRT_Inference_Benchmark.pdf",
                    docType = DocumentType.RESEARCH_PAPER,
                    ocrStatus = OcrProcessingStatus.VERIFIED,
                    extractedSummary = "Latency recorded: 14.2ms @ FP16 on Jetson Orin Nano. 98.4% precision on industrial circuit flaw dataset.",
                    fileSizeBytes = "6.4 MB",
                    confidenceScore = 0.99f
                ),
                DocumentEntity(
                    projectId = 3,
                    title = "GaN_Inverter_Safety_Isolation_Checklist.pdf",
                    docType = DocumentType.SUPERVISOR_EVALUATION,
                    ocrStatus = OcrProcessingStatus.NEEDS_REVIEW,
                    extractedSummary = "High voltage thermal test logged peak 64°C at 1.2kW load. Pending faculty supervisor physical sign-off.",
                    fileSizeBytes = "1.5 MB",
                    confidenceScore = 0.89f
                ),
                DocumentEntity(
                    projectId = 5,
                    title = "Indus_Nexus_OCR_Engine_Evaluation_FYP.pdf",
                    docType = DocumentType.OCR_REPORT,
                    ocrStatus = OcrProcessingStatus.VERIFIED,
                    extractedSummary = "Automatic optical character verification accuracy benchmarked at 99.2% on engineering schematics and laboratory stamp records.",
                    fileSizeBytes = "3.7 MB",
                    confidenceScore = 0.99f
                )
            )
            documentDao.insertAllDocuments(sampleDocuments)
        }
    }
}
