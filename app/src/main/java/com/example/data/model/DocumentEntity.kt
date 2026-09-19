package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class DocumentType {
    SCHEMATIC,
    RESEARCH_PAPER,
    OCR_REPORT,
    SUPERVISOR_EVALUATION,
    BOM_INVENTORY
}

enum class OcrProcessingStatus {
    VERIFIED,
    PROCESSING,
    NEEDS_REVIEW
}

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val projectId: Long,
    val title: String,
    val docType: DocumentType,
    val ocrStatus: OcrProcessingStatus,
    val extractedSummary: String,
    val fileSizeBytes: String,
    val confidenceScore: Float,
    val uploadedAtEpoch: Long = System.currentTimeMillis()
)
