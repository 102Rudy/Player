package com.rygital.core.data

import com.rygital.core.model.AudioFile

interface AudioFileRepository {
    suspend fun setAudioFiles(items: List<AudioFile>)
    suspend fun getAudioFiles(): List<AudioFile>
}