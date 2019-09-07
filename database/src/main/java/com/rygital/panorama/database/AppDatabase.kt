package com.rygital.panorama.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rygital.panorama.database.dao.AudioFileDao
import com.rygital.panorama.database.entity.AudioFileEntity

@Database(entities = [AudioFileEntity::class], version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun audioFileDao(): AudioFileDao
}