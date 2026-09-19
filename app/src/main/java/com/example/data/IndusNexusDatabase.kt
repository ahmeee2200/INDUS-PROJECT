package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.DocumentDao
import com.example.data.dao.ProjectDao
import com.example.data.model.DocumentEntity
import com.example.data.model.ProjectEntity

@Database(
    entities = [
        ProjectEntity::class,
        DocumentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class IndusNexusDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun documentDao(): DocumentDao

    companion object {
        @Volatile
        private var INSTANCE: IndusNexusDatabase? = null

        fun getInstance(context: Context): IndusNexusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IndusNexusDatabase::class.java,
                    "indus_nexus_lab.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
