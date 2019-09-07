package com.rygital.core.data

import com.rygital.core.model.AudioFile

interface AudioFileRepository {
    fun setAudioFiles(items: List<AudioFile>)
    fun getAudioFiles(): List<AudioFile>
}