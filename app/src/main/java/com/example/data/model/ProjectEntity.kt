package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ProjectCategory {
    HARDWARE,
    SOFTWARE,
    HYBRID_IOT
}

enum class ProjectStatus {
    IN_PROGRESS,
    BENCH_TESTING,
    READY_FOR_DEFENSE,
    DOCUMENTATION_REVIEW
}

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val projectCode: String,
    val title: String,
    val category: ProjectCategory,
    val labDepartment: String,
    val status: ProjectStatus,
    val supervisorName: String,
    val leadStudent: String,
    val teamMembersCount: Int,
    val hardwareModules: String, // e.g. "STM32F407 Microcontroller, CAN-Bus Transceiver, 9-DOF IMU"
    val softwareStack: String,   // e.g. "FreeRTOS Kernel, C/C++, ROS2 Humble, Jetpack Compose"
    val benchLocation: String,   // e.g. "BENCH-04B", "RACK-02"
    val completionPercent: Int,
    val documentsCount: Int,
    val ocrVerified: Boolean,
    val lastUpdatedEpoch: Long = System.currentTimeMillis()
)
