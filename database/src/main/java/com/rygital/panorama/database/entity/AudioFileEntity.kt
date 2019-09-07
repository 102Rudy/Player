package com.rygital.panorama.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class AudioFileEntity(
        @PrimaryKey(autoGenerate = true) var id: Long,
        var title: String,
        var path: String
)